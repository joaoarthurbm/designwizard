package org.designwizard.mcp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.Entity;
import org.designwizard.exception.InexistentEntityException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.TextContent;

/**
 * Shared helpers for building tool results and resolving DesignWizard entities.
 * Kept free of any single tool's business logic so EntityTools/RuleTools stay focused
 * on what each tool actually does.
 */
final class Tools {

	private Tools() {
	}

	static final ObjectMapper JSON = new ObjectMapper();

	static CallToolResult ok(Object structured) {
		String text;
		try {
			text = JSON.writerWithDefaultPrettyPrinter().writeValueAsString(structured);
		}
		catch (IOException e) {
			return error("Failed to serialize result: " + e.getMessage());
		}
		return CallToolResult.builder().content(List.of(new TextContent(null, text, null))).isError(false).build();
	}

	static CallToolResult error(String message) {
		return CallToolResult.builder().content(List.of(new TextContent(null, message, null))).isError(true).build();
	}

	@FunctionalInterface
	interface ToolBody {

		CallToolResult run() throws IOException, InexistentEntityException;

	}

	/**
	 * Runs a tool handler body and converts any failure into an error CallToolResult.
	 * Deliberately catches {@link Throwable}, not just the checked exceptions DesignWizard
	 * declares: an uncaught Error (e.g. NoClassDefFoundError from a missing optional
	 * dependency like ASM) previously killed the reactor worker thread silently, leaving
	 * the calling agent waiting forever for a response that would never come. A tool that
	 * fails fast with a clear message is far better than one that hangs the caller.
	 */
	static CallToolResult guard(ToolBody body) {
		try {
			return body.run();
		}
		catch (IllegalArgumentException e) {
			return error(e.getMessage());
		}
		catch (InexistentEntityException e) {
			return error("Entity not found: " + e.getMessage());
		}
		catch (IOException e) {
			return error("Failed to analyze path: " + e.getMessage());
		}
		catch (Throwable t) {
			return error("Unexpected failure: " + t);
		}
	}

	static String requireString(Map<String, Object> arguments, String name) {
		Object value = arguments == null ? null : arguments.get(name);
		if (!(value instanceof String) || ((String) value).isEmpty()) {
			throw new IllegalArgumentException("Missing required argument: " + name);
		}
		return (String) value;
	}

	static String optionalString(Map<String, Object> arguments, String name) {
		Object value = arguments == null ? null : arguments.get(name);
		return value instanceof String && !((String) value).isEmpty() ? (String) value : null;
	}

	@SuppressWarnings("unchecked")
	static List<String> optionalStringList(Map<String, Object> arguments, String name) {
		Object value = arguments == null ? null : arguments.get(name);
		return value instanceof List ? (List<String>) value : null;
	}

	/** Sorted names of a collection of entities -- used for the caller/callee sets on Entity. */
	static List<String> names(Collection<? extends Entity> entities) {
		List<String> result = new ArrayList<>();
		for (Entity e : entities) {
			result.add(e.getName());
		}
		Collections.sort(result);
		return result;
	}

	/** Common descriptive fields every DesignWizard Entity (class/method/field/package) exposes. */
	static Map<String, Object> entityInfo(Entity entity) {
		Map<String, Object> info = new LinkedHashMap<>();
		info.put("name", entity.getName());
		info.put("shortName", entity.getShortName());
		info.put("entityType", entity.getTypeOfEntity().toString());
		info.put("declaringClassName", entity.getClassName());
		info.put("package", entity.getPackage() != null ? entity.getPackage().getName() : null);
		info.put("abstract", entity.isAbstract());
		info.put("modifiers", entity.getModifiers().toString());
		info.put("visibility", String.valueOf(entity.getVisibility()));
		info.put("annotations", names(entity.getAnnotations()));
		return info;
	}

	/** Resolves an entity by the uniform DesignWizard.api.DesignWizard lookup methods, keyed by "type". */
	static Entity resolveEntity(DesignWizard dw, String type, String name) throws InexistentEntityException {
		switch (type) {
			case "class":
				return dw.getClass(name);
			case "method":
				return dw.getMethod(name);
			case "field":
				return dw.getField(name);
			case "package":
				return dw.getPackage(name);
			case "annotation":
				return dw.getAnnotation(name);
			default:
				throw new IllegalArgumentException(
						"Unknown entity type '" + type + "'; expected class, method, field, package or annotation");
		}
	}

}
