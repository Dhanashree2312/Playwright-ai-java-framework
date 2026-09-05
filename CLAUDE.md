# Playwright AI Automation Framework

## Project Overview

This is a Java-based test automation framework built using Playwright, TestNG, Maven, and Apache POI.

The framework supports:

* UI automation using Playwright
* API automation using Playwright APIRequestContext
* Page Object Model
* PageManager
* ThreadLocal-based browser management
* Data-driven testing using Excel
* TestNG execution
* Extent Reports
* Failure screenshots
* AI-based test case generation
* Local LLM integration using Ollama
* AI-assisted/self-healing locator functionality

## Technology Stack

* Java 21
* Playwright 1.55.0
* TestNG 7.11.0
* Maven
* Apache POI
* Extent Reports
* Gson
* Ollama
* Qwen2.5-Coder 7B

## Project Structure

```text
playwright-ai-framework/
│
├── CLAUDE.md
├── pom.xml
│
├── config/
│   └── config.properties
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── ai/
│   │       ├── framework/
│   │       └── pages/
│   │
│   └── test/
│       └── java/
│           └── tests/
│
└── target/
```

## Coding Guidelines

1. Use Java 21 features where appropriate.
2. Follow Page Object Model for UI pages.
3. Keep test classes focused on test scenarios.
4. Keep browser initialization and cleanup inside the framework layer.
5. Avoid hard-coded credentials.
6. Store configuration values in the configuration file.
7. Reuse existing framework utilities before creating new utilities.
8. Do not duplicate existing functionality.
9. Keep AI-generated output validated before using it in automation.
10. Do not introduce unnecessary dependencies.

## Playwright Guidelines

* Use Playwright locators instead of brittle selectors whenever possible.
* Prefer stable selectors such as role, label, text, and test IDs.
* Use appropriate Playwright waits rather than Thread.sleep.
* Keep page-specific locators and actions inside Page Object classes.
* API calls should use Playwright's APIRequestContext.

## Test Execution

Run the complete test suite using:

```bash
mvn clean test
```

A successful execution should end with:

```text
BUILD SUCCESS
```

## AI / Ollama Integration

The framework communicates with a locally running Ollama server.

Default Ollama endpoint:

```text
http://localhost:11434
```

Current model:

```text
qwen2.5-coder:7b
```

The AI layer is used for:

* Generating test cases from requirements
* Returning structured JSON test cases
* Assisting with locator/self-healing scenarios

### Important

Ollama must be running before executing AI-dependent tests.

Verify the available models with:

```powershell
curl.exe http://localhost:11434/api/tags
```

## AI Response Handling

AI responses may contain Markdown code fences such as:

````text
```json
{ ... }
````

````

The framework should clean the response before attempting JSON parsing.

AI output should always be treated as untrusted generated content and validated before being used.

## Reporting

Extent Reports are generated under the `target` directory according to the framework's reporting configuration.

Failure screenshots are captured only when a test fails.

Therefore, the absence of a `target/screenshots` directory after a completely successful run is expected.

## Important Rule for Future Changes

Before modifying the framework:

1. Understand the existing implementation.
2. Reuse existing utilities where possible.
3. Make the smallest required change.
4. Run the affected test.
5. Run the complete suite with:

```bash
mvn clean test
````

6. Do not change working functionality unnecessarily.

## Git Guidelines

Do not commit generated build artifacts or local IDE files.

The repository should generally exclude:

```text
target/
.idea/
*.iml
```

Do not commit:

* passwords
* API keys
* tokens
* private credentials
* local machine-specific configuration

## Framework Goal

The goal of this project is to provide a maintainable automation framework that combines traditional Playwright automation with AI-assisted test generation and self-healing capabilities while keeping the framework simple, reliable, and easy to maintain.