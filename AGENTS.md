# AGENTS.md

Operational instructions for coding agents working in this repository. This file is
tool-agnostic (Claude Code, Codex, Cursor, etc.). It does not cover domain vocabulary
or architecture — only build/test/style/contribution mechanics.

## Required toolchain: JDK 8 (not 9+)

`pom.xml` pins `<source>`/`<target>` to `1.7`. Modern `javac` (JDK 9+) refuses that:

```
[ERROR] Source option 7 is no longer supported. Use 8 or later.
```

This was confirmed on this machine with JDK 21 (Homebrew). Before running any Maven
command, make sure `JAVA_HOME` points at a real JDK 8 — not JDK 9+, and not a Java
"applet plugin" JRE (those don't work with Maven).

```
brew install --cask temurin@8
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
```

**Unverified caveat**: the Surefire `argLine` also sets `-XX:-UseSplitVerifier`, a
JVM flag from the Java 6/7 era. Whether a real JDK 8 still accepts it (vs. failing at
test-launch) has not been empirically confirmed in this repo — only the compile-time
failure on JDK 9+ has. If `mvn test` fails at the JVM-launch stage even under JDK 8,
that flag is the first thing to check.

## Build & test

Maven is the canonical, CI-facing build (Travis defaults to Maven when `pom.xml` is
present, and that's what's actually exercised on every push).

```
mvn test          # compile + run JUnit 4 tests (src_tests/) + JaCoCo coverage
mvn package        # build the jar
```

`build.xml` (Ant) is legacy. It still works for producing `dist/` artifacts and
Javadoc, but is not the primary path — don't assume it needs to stay in sync with
Maven changes unless asked.

## Code style

`checkstyle-config.xml` exists at the repo root as a style reference, but it is
**not wired into the Maven build** — it won't fail a build or a CI run today. Use it
as a guide for formatting/naming when editing Java code; don't treat a violation as a
blocking error unless asked to actually integrate the plugin.

## Known constraints and gotchas

- **Java 7 bytecode ceiling for analyzed code**: DesignWizard uses ASM 3.1 to parse
  the bytecode of the code *it analyzes*. ASM 3.1 cannot parse Java 8 lambda
  expressions in that target code (see [issue #36](https://github.com/joaoarthurbm/designwizard/issues/36)).
  This is separate from the JDK-8-to-build-DesignWizard-itself requirement above.
- **`dist/` and `lib/` are committed binaries, not build output.** Unlike `bin/`,
  `classes/`, and `target/` (which are gitignored), the jars under `dist/` (e.g.
  `designwizard-1.4.jar`) and `lib/` (e.g. `asm-3.1.jar`) are tracked in git on
  purpose. Never regenerate, delete, or overwrite them as if cleaning build output —
  only touch them for a deliberate, explicit dependency/version update.

## Contribution workflow

No strict commit-message convention is enforced — existing history is free-form,
imperative-mood messages. Match that style; don't introduce a new convention (e.g.
Conventional Commits) unless asked.

The project's contribution flow (fork, feature branch, PR) is documented in
[README.md](./README.md); follow it for anything meant to land upstream.
