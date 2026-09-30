package org.designwizard.mcp;

import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.jackson2.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Entry point for the DesignWizard MCP server: exposes DesignWizard's architecture
 * queries and design-rule checks as MCP tools over stdio, for any already-compiled
 * Java project supplied per tool call via a "path" argument.
 * <p>
 * Requires the {@code path} passed to any tool to already be compiled (a .jar, or a
 * directory of .class files) -- this server does not build target projects itself.
 */
public final class Main {

	private Main() {
	}

	public static void main(String[] args) throws InterruptedException {
		McpJsonMapper jsonMapper = new JacksonMcpJsonMapper(new ObjectMapper());
		StdioServerTransportProvider transportProvider = new StdioServerTransportProvider(jsonMapper);
		DesignWizardCache cache = new DesignWizardCache();

		McpServer.SyncSpecification<?> builder = McpServer.sync(transportProvider)
			.serverInfo("designwizard-mcp", "0.1.0")
			.capabilities(McpSchema.ServerCapabilities.builder().tools(false).build());

		EntityTools.register(builder, cache, jsonMapper);
		RuleTools.register(builder, cache, jsonMapper);

		McpSyncServer server = builder.build();
		Runtime.getRuntime().addShutdownHook(new Thread(server::close));

		new CountDownLatch(1).await();
	}

}
