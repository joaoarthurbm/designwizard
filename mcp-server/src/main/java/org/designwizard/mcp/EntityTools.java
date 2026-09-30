package org.designwizard.mcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Registers the four "read the architecture" tools: find_entity, list_entities,
 * get_relationships and get_impact_of_change. All operate on the uniform
 * org.designwizard.design.Entity contract, so one code path serves classes, methods,
 * fields, packages and annotations alike.
 */
final class EntityTools {

	private EntityTools() {
	}

	private static final String ENTITY_TYPE_SCHEMA_FRAGMENT = "\"enum\": [\"class\", \"method\", \"field\", \"package\", \"annotation\"]";

	static void register(McpServer.SyncSpecification<?> builder, DesignWizardCache cache, McpJsonMapper jsonMapper) {
		builder.toolCall(findEntityTool(jsonMapper), (exchange, request) -> findEntity(cache, request.arguments()));
		builder.toolCall(listEntitiesTool(jsonMapper), (exchange, request) -> listEntities(cache, request.arguments()));
		builder.toolCall(getRelationshipsTool(jsonMapper),
				(exchange, request) -> getRelationships(cache, request.arguments()));
		builder.toolCall(getImpactOfChangeTool(jsonMapper),
				(exchange, request) -> getImpactOfChange(cache, request.arguments()));
	}

	// --- find_entity ---------------------------------------------------

	private static McpSchema.Tool findEntityTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "type": { "type": "string", %s },
				    "name": { "type": "string", "description": "Fully qualified DesignWizard name, e.g. foo.bar.MyClass, foo.bar.MyClass.method(int), foo.bar.MyClass.field, or a package name" }
				  },
				  "required": ["path", "type", "name"]
				}
				""".formatted(ENTITY_TYPE_SCHEMA_FRAGMENT);
		return McpSchema.Tool.builder("find_entity", jsonMapper, schema)
			.description("Looks up a single class, method, field, package or annotation by its fully qualified "
					+ "name and returns its descriptive facts (modifiers, visibility, annotations, etc.).")
			.build();
	}

	private static McpSchema.CallToolResult findEntity(DesignWizardCache cache, Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String type = Tools.requireString(arguments, "type");
			String name = Tools.requireString(arguments, "name");
			DesignWizard dw = cache.get(path);
			Entity entity = Tools.resolveEntity(dw, type, name);
			return Tools.ok(Tools.entityInfo(entity));
		});
	}

	// --- list_entities ---------------------------------------------------

	private static McpSchema.Tool listEntitiesTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "type": { "type": "string", %s },
				    "pattern": { "type": "string", "description": "Optional regular expression to filter results by fully qualified name" }
				  },
				  "required": ["path", "type"]
				}
				""".formatted(ENTITY_TYPE_SCHEMA_FRAGMENT);
		return McpSchema.Tool.builder("list_entities", jsonMapper, schema)
			.description("Lists all classes, methods, fields, packages or annotations in the analyzed codebase, "
					+ "optionally filtered by a regular expression on the name. Fields are gathered from every "
					+ "class's declared fields, since DesignWizard has no global field listing of its own.")
			.build();
	}

	private static McpSchema.CallToolResult listEntities(DesignWizardCache cache, Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String type = Tools.requireString(arguments, "type");
			String pattern = Tools.optionalString(arguments, "pattern");
			DesignWizard dw = cache.get(path);

			List<String> names = new ArrayList<>();
			switch (type) {
				case "class":
					for (ClassNode c : (pattern != null ? dw.getClasses(pattern) : dw.getAllClasses())) {
						names.add(c.getName());
					}
					break;
				case "method":
					for (var m : (pattern != null ? dw.getMethods(pattern) : dw.getAllMethods())) {
						names.add(m.getName());
					}
					break;
				case "package":
					for (var p : (pattern != null ? dw.getPackages(pattern) : dw.getAllPackages())) {
						names.add(p.getName());
					}
					break;
				case "annotation": {
					Pattern compiled = pattern != null ? Pattern.compile(pattern) : null;
					for (ClassNode a : dw.getAllAnnotations()) {
						if (compiled == null || compiled.matcher(a.getName()).matches()) {
							names.add(a.getName());
						}
					}
					break;
				}
				case "field": {
					Pattern compiled = pattern != null ? Pattern.compile(pattern) : null;
					for (ClassNode c : dw.getAllClasses()) {
						for (FieldNode f : c.getDeclaredFields()) {
							if (compiled == null || compiled.matcher(f.getName()).matches()) {
								names.add(f.getName());
							}
						}
					}
					break;
				}
				default:
					return Tools.error(
							"Unknown entity type '" + type + "'; expected class, method, field, package or annotation");
			}
			Collections.sort(names);

			Map<String, Object> result = new LinkedHashMap<>();
			result.put("type", type);
			result.put("count", names.size());
			result.put("names", names);
			return Tools.ok(result);
		});
	}

	// --- get_relationships ---------------------------------------------------

	private static McpSchema.Tool getRelationshipsTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "type": { "type": "string", %s },
				    "name": { "type": "string", "description": "Fully qualified DesignWizard name of the entity" }
				  },
				  "required": ["path", "type", "name"]
				}
				""".formatted(ENTITY_TYPE_SCHEMA_FRAGMENT);
		return McpSchema.Tool.builder("get_relationships", jsonMapper, schema)
			.description("Returns who calls this entity and what it calls, at method, class and package "
					+ "granularity in one response.")
			.build();
	}

	private static McpSchema.CallToolResult getRelationships(DesignWizardCache cache, Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String type = Tools.requireString(arguments, "type");
			String name = Tools.requireString(arguments, "name");
			DesignWizard dw = cache.get(path);
			Entity entity = Tools.resolveEntity(dw, type, name);

			Map<String, Object> result = new LinkedHashMap<>();
			result.put("entity", entity.getName());
			result.put("callerMethods", Tools.names(entity.getCallerMethods()));
			result.put("calleeMethods", Tools.names(entity.getCalleeMethods()));
			result.put("callerClasses", Tools.names(entity.getCallerClasses()));
			result.put("calleeClasses", Tools.names(entity.getCalleeClasses()));
			result.put("callerPackages", Tools.names(entity.getCallerPackages()));
			result.put("calleePackages", Tools.names(entity.getCalleePackages()));
			return Tools.ok(result);
		});
	}

	// --- get_impact_of_change ---------------------------------------------------

	private static McpSchema.Tool getImpactOfChangeTool(McpJsonMapper jsonMapper) {
		String schema = """
				{
				  "type": "object",
				  "properties": {
				    "path": { "type": "string", "description": "Path to the compiled .jar or directory of .class files to analyze" },
				    "type": { "type": "string", %s },
				    "name": { "type": "string", "description": "Fully qualified DesignWizard name of the entity" }
				  },
				  "required": ["path", "type", "name"]
				}
				""".formatted(ENTITY_TYPE_SCHEMA_FRAGMENT);
		return McpSchema.Tool.builder("get_impact_of_change", jsonMapper, schema)
			.description("Traces what would be affected by changing this entity, up to the depth configured in "
					+ "designwizard.properties (default 7). Each item in 'impact' is one call-chain trace.")
			.build();
	}

	private static McpSchema.CallToolResult getImpactOfChange(DesignWizardCache cache, Map<String, Object> arguments) {
		return Tools.guard(() -> {
			String path = Tools.requireString(arguments, "path");
			String type = Tools.requireString(arguments, "type");
			String name = Tools.requireString(arguments, "name");
			DesignWizard dw = cache.get(path);
			Entity entity = Tools.resolveEntity(dw, type, name);

			List<List<String>> trace = new ArrayList<>();
			for (String[] line : entity.getImpactOfAChange()) {
				trace.add(Arrays.asList(line));
			}

			Map<String, Object> result = new LinkedHashMap<>();
			result.put("entity", entity.getName());
			result.put("impact", trace);
			return Tools.ok(result);
		});
	}

}
