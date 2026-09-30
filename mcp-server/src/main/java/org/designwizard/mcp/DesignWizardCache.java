package org.designwizard.mcp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.designwizard.api.DesignWizard;

/**
 * Caches one {@link DesignWizard} instance per analyzed path, keyed by the path as
 * given by the caller. Construction re-extracts facts from bytecode (ASM scan), so
 * tools reuse the same instance across calls instead of rebuilding it every time.
 * <p>
 * DesignWizard's extractor has at least one leftover debug statement that writes
 * straight to {@code System.out} for certain bytecode shapes (observed: any class
 * signature containing "ILegacyMatcherMethods", e.g. from EasyMock -- see
 * DesignManager.signatureExtracted). On a stdio MCP server, stdout is the protocol
 * channel itself, so any stray write there corrupts every subsequent JSON-RPC message.
 * Construction is the only point that runs the extractor (queries afterwards just read
 * the already-built model), so redirecting stdout for that one call is enough to
 * neutralize this class of bug without touching DesignWizard's own source.
 */
final class DesignWizardCache {

	private static final Object EXTRACTION_LOCK = new Object();

	private final ConcurrentMap<String, DesignWizard> byPath = new ConcurrentHashMap<>();

	DesignWizard get(String path) throws IOException {
		DesignWizard cached = this.byPath.get(path);
		if (cached != null) {
			return cached;
		}
		DesignWizard created = withStdoutSuppressed(() -> new DesignWizard(path));
		DesignWizard existing = this.byPath.putIfAbsent(path, created);
		return existing != null ? existing : created;
	}

	@FunctionalInterface
	private interface ExtractingConstructor {

		DesignWizard construct() throws IOException;

	}

	private static DesignWizard withStdoutSuppressed(ExtractingConstructor constructor) throws IOException {
		synchronized (EXTRACTION_LOCK) {
			PrintStream realStdout = System.out;
			System.setOut(new PrintStream(OutputStream.nullOutputStream()));
			try {
				return constructor.construct();
			}
			finally {
				System.setOut(realStdout);
			}
		}
	}

}
