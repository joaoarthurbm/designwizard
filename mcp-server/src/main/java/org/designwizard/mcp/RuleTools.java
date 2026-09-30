package org.designwizard.mcp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.designrules.AbstractDependencesRule;
import org.designwizard.designrules.ClassDependencesRule;
import org.designwizard.designrules.CyclicDependencyRule;
import org.designwizard.designrules.HashCodeAndEqualsRule;
import org.designwizard.designrules.MethodDependencesRule;
import org.designwizard.designrules.PackageDependences;
import org.designwizard.patternchecker.CheckError;
import org.designwizard.patternchecker.CheckWarning;
import org.designwizard.patternchecker.CheckingResult;
import org.designwizard.patternchecker.SingletonPatternChecker;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Registers the four design-audit tools: check_dependency_rule, check_cyclic_dependencies,
 * check_hashcode_equals and check_singleton_pattern. Kept separate from each other (rather
 * than collapsed into one tool) because each wraps a Rule/PatternChecker implementation
 * with a genuinely different constructor and report shape -- see the grilling session
 * that designed this module for why ClassDependencesRule/MethodDependencesRule/
 * PackageDependences DO collapse into one tool (check_dependency_rule) but these don't.
 */
final class RuleTools {

	private RuleTools() {
	}

	static void register(McpServer.SyncSpecification<?> builder, DesignWizardCache cache, McpJsonMapper jsonMapper) {
		builder.toolCall(checkDependencyRuleTool(jsonMapper),
				(exchange, request) -> checkDependencyRule(cache, request.arguments()));
		builder.toolCall(checkCyclicDependenciesTool(jsonMapper),
				(exchange, request) -> checkCyclicDependencies(cache, request.arguments()));
		builder.toolCall(checkHashCodeAndEqualsTool(jsonMapper),
				(exchange, request) -> checkHashCodeAndEquals(cache, request.arguments()));
		builder.toolCall(checkSingletonPatternTool(jsonMapper),
				(exchange, request) -> checkSingletonPattern(cache, request.arguments()));
	}

	// --- check_dependency_rule ---------------------------------------------------

	private static McpSchema.Tool checkDependencyRuleTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "scope": { "type": "string", "enum": ["class", "method", "package"] },
				    "entity": { "type": "string", "description": "Fully qualified name of the class/method/package to check" },
				    "allowed": {
				      "type": "array", "items": { "type": "string" },
				      "description": "Exhaustive allow-list: if given, the entity must depend on ONLY these (anything else is a violation)"
				    },
				    "violations": {
				      "type": "array", "items": { "type": "string" },
				      "description": "Deny-list: if given (and 'allowed' is omitted), any of these the entity depends on is reported"
				    }
				  },
				  "required": ["path", "scope", "entity"]
				}
				""";
		return McpSchema.Tool.builder("check_dependency_rule", jsonMapper, schema)
			.description("Checks a class/method/package's dependencies against an allow-list or deny-list. "
					+ "Covers DesignWizard's ClassDependencesRule, MethodDependencesRule and PackageDependences, "
					+ "which share an identical contract and differ only by scope.")
			.build();
	}

	private static McpSchema.CallToolResult checkDependencyRule(DesignWizardCache cache, Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String scope = Tools.requireString(arguments, "scope");
			String entityName = Tools.requireString(arguments, "entity");
			List<String> allowed = Tools.optionalStringList(arguments, "allowed");
			List<String> violations = Tools.optionalStringList(arguments, "violations");
			DesignWizard dw = cache.get(path);

			AbstractDependencesRule rule;
			switch (scope) {
				case "class":
					rule = new ClassDependencesRule(entityName, dw);
					break;
				case "method":
					rule = new MethodDependencesRule(entityName, dw);
					break;
				case "package":
					rule = new PackageDependences(entityName, dw);
					break;
				default:
					return Tools.error("Unknown scope '" + scope + "'; expected class, method or package");
			}
			if (allowed != null) {
				rule.addAllowedEntities(allowed.toArray(new String[0]));
			}
			if (violations != null) {
				rule.addDeniedEntities(violations.toArray(new String[0]));
			}

			boolean compliant = rule.checkRule();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("entity", entityName);
			result.put("scope", scope);
			result.put("compliant", compliant);
			result.put("report", rule.getReport());
			return Tools.ok(result);
		});
	}

	// --- check_cyclic_dependencies ---------------------------------------------------

	private static McpSchema.Tool checkCyclicDependenciesTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" }
				  },
				  "required": ["path"]
				}
				""";
		return McpSchema.Tool.builder("check_cyclic_dependencies", jsonMapper, schema)
			.description("Scans every package in the analyzed codebase for cyclic package dependencies.")
			.build();
	}

	private static McpSchema.CallToolResult checkCyclicDependencies(DesignWizardCache cache,
			Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			DesignWizard dw = cache.get(path);
			CyclicDependencyRule rule = new CyclicDependencyRule(dw);
			boolean compliant = rule.checkRule();

			Map<String, Object> result = new LinkedHashMap<>();
			result.put("compliant", compliant);
			result.put("report", rule.getReport());
			return Tools.ok(result);
		});
	}

	// --- check_hashcode_equals ---------------------------------------------------

	private static McpSchema.Tool checkHashCodeAndEqualsTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "className": { "type": "string", "description": "Fully qualified name of the class to check" }
				  },
				  "required": ["path", "className"]
				}
				""";
		return McpSchema.Tool.builder("check_hashcode_equals", jsonMapper, schema)
			.description("Checks whether a class overrides both equals() and hashCode(), or neither -- "
					+ "overriding only one is reported as a violation.")
			.build();
	}

	private static McpSchema.CallToolResult checkHashCodeAndEquals(DesignWizardCache cache,
			Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String className = Tools.requireString(arguments, "className");
			DesignWizard dw = cache.get(path);
			HashCodeAndEqualsRule rule = new HashCodeAndEqualsRule(className, dw);
			boolean compliant = rule.checkRule();

			Map<String, Object> result = new LinkedHashMap<>();
			result.put("class", className);
			result.put("compliant", compliant);
			result.put("report", rule.getReport());
			return Tools.ok(result);
		});
	}

	// --- check_singleton_pattern ---------------------------------------------------

	private static McpSchema.Tool checkSingletonPatternTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "className": { "type": "string", "description": "Fully qualified name of the class to check" }
				  },
				  "required": ["path", "className"]
				}
				""";
		return McpSchema.Tool.builder("check_singleton_pattern", jsonMapper, schema)
			.description("Checks whether a class correctly implements the Singleton pattern (private/static "
					+ "instance field, no public constructor, a working instance provider).")
			.build();
	}

	private static McpSchema.CallToolResult checkSingletonPattern(DesignWizardCache cache,
			Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String className = Tools.requireString(arguments, "className");
			DesignWizard dw = cache.get(path);
			ClassNode classNode = dw.getClass(className);

			SingletonPatternChecker checker = new SingletonPatternChecker(classNode);
			CheckingResult checkResult = checker.verify();

			List<String> errors = new ArrayList<>();
			for (CheckError e : checkResult.getErrors()) {
				errors.add(e.toString());
			}
			List<String> warnings = new ArrayList<>();
			for (CheckWarning w : checkResult.getWarnings()) {
				warnings.add(w.toString());
			}

			Map<String, Object> result = new LinkedHashMap<>();
			result.put("class", className);
			result.put("compliant", checkResult.getVeredict());
			result.put("errors", errors);
			result.put("warnings", warnings);
			return Tools.ok(result);
		});
	}

}
