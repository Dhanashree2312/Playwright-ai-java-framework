# Playwright AI Java Automation Framework

A Java-based test automation framework combining **Playwright UI automation, API testing, and AI-powered capabilities** using a locally hosted Ollama LLM.

## 🚀 Tech Stack

* Java 21
* Playwright
* TestNG
* Maven
* REST API automation
* Extent Reports
* Apache POI / Excel test data
* Ollama / Local LLM
* JSON processing

## 📁 Project Structure

```text
playwright-ai-framework/
│
├── ai-engine/
│   └── .venv/                  # Python virtual environment (ignored)
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── ai/             # Ollama client and AI models
│   │   │   ├── api/            # API client utilities
│   │   │   ├── framework/      # Configuration
│   │   │   └── utils/          # JSON and response utilities
│   │   │
│   │   └── resources/
│   │
│   └── test/
│       ├── java/
│       │   ├── ai/              # AI and self-healing components
│       │   ├── api/             # API test support
│       │   ├── framework/       # Test framework components
│       │   ├── listeners/       # TestNG listeners
│       │   ├── models/          # Request/response models
│       │   ├── pages/            # Page Objects
│       │   └── tests/            # Test cases
│       │
│       └── resources/
│           ├── config/
│           └── testdata/
│
├── CLAUDE.md
├── pom.xml
├── testng.xml
└── .gitignore
```

## ✨ Key Features

### UI Automation

* Playwright with Java
* Page Object Model
* Browser and context management
* TestNG integration
* Screenshot capture
* Parallel execution support

### API Automation

* REST API testing
* Reusable API clients
* Request/response validation
* JSON processing
* Data-driven API tests

### Test Data Management

* Excel-based test data using Apache POI
* TestNG data providers
* External configuration through properties files

### Reporting

* Extent Reports
* TestNG listeners
* Failure screenshots

### 🤖 AI Capabilities

The framework integrates with **Ollama** to explore AI-assisted test automation.

Current components include:

* Ollama client integration
* AI response analysis
* AI-generated test cases
* Self-healing locators
* Self-healing API responses
* AI-assisted Playwright testing

## ▶️ Running Tests

Make sure Java and Maven are installed and configured.

Run the complete TestNG suite:

```bash
mvn test
```

Run a specific test class:

```bash
mvn -Dtest=LoginTest test
```

## 🤖 Running AI Features

The AI components use a locally running Ollama instance.

Make sure Ollama is running and the required model is available before executing AI-related tests.

## 🔐 Configuration

Environment-specific configuration should be kept outside source control where appropriate.

Do **not** commit API keys, passwords, `.env` files, or other secrets.

## 📊 Test Reports

Test execution generates reports and test artifacts under the Maven `target` directory.

Generated artifacts are excluded from Git using `.gitignore`.

## 🎯 Project Goal

This project is being developed as a **modern QA automation framework** that combines traditional UI/API automation with **Generative AI and self-healing capabilities**.

The goal is to explore how AI can improve test creation, analysis, maintenance, and automation reliability.