
# 🎬 TMDB-Go: TMDB CLI Tool

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2+-green?style=for-the-badge&logo=springboot)
![Build](https://img.shields.io/badge/Build-Gradle-blue?style=for-the-badge)

> A professional Command Line Interface (CLI) for searching movies, built to demonstrate modern Spring Boot architecture, reactive client patterns, and clean code principles.

---

## 📖 Overview
`![Demo Screenshot](assets/demo.png)`

**TMDB-Bo** is an interactive terminal application that allows users to query The Movie Database (TMDB) instantly. This project mimics enterprise-grade software architecture by implementing **Clean Architecture**, **Caching**, **Resilience patterns**, and **Type-safe configuration**.

###  Key Features
* **Interactive Shell:** Built with Spring Shell for a tab-complete experience.
* **Smart Caching:** Repeated searches hit local memory instead of the API.
* **Visual Output:** Renders ASCII tables and ANSI colors for readability.
* **Robust Error Handling:** Friendly error messages for network/API failures (no stack traces).
* **Secure Config:** API keys are managed via Environment Variables.

---

##  Tech Stack

* **Language:** Java 21 (LTS) - Utilizing Records & Virtual Threads.
* **Framework:** Spring Boot 3.x
* **CLI:** Spring Shell
* **HTTP Client:** Spring `RestClient` (Fluent API)
* **Testing:** JUnit 5, Mockito, WireMock (for offline integration testing)
* **Build Tool:** Gradle

---

## 🚀 Getting Started

### Prerequisites
1.  **Java 21** installed.
2.  **TMDB API Key** (Get it free at [themoviedb.org](https://www.themoviedb.org/documentation/api)).

###  Configuration
This project **does not** hardcode API keys. You must set it as an Environment Variable.

**Option A: IntelliJ / IDE**
1.  Run Configuration -> Edit Configurations.
2.  Environment Variables: `TMDB_API_KEY=your_actual_key_here`

**Option B: Terminal (Linux/Mac)**
```bash
export TMDB_API_KEY=your_actual_key_here
./mvnw spring-boot:run
```
**Option C: Terminal (Windows PowerShell)**
```bash
$env:TMDB_API_KEY="your_actual_key_here" ./mvnw spring-boot:run
```
---
###  Usage Guide
Once the application starts, you will enter the interactive shell:
```bash
shell:>
```
**Available Commands**
|Command|Description|Example|
|--|--|--|
| ``search`` |Search for a movie by title |``search Star Wars``  |
| ``clear-cache`` |Wipes the local cache to force fresh data. |``clear-cache``  |
| ``help`` |Lists all available commands. |``help``  |

**Example Output**
```bash
shell:> search "Inception"
+------------------------------------------+------------+----------+
| TITLE                                    | RELEASED   | RATING   |
+------------------------------------------+------------+----------+
| Inception                                | 2010-07-15 | ⭐ 8.4   |
| Inception: The Cobol Job                 | 2010-07-14 | ⭐ 7.2   |
+------------------------------------------+------------+----------+
```
---
###  Architecture
This project follows a strict **Separation of Concerns**:

1.  **Command Layer (`command`):** Handles user input and formatting (UI). Logic-free.

2.  **Service Layer (`service`):** Business logic, caching, and data processing.

3.  **Client Layer (`client`):** Wrapper for the external TMDB API.

4.  **Model Layer (`model`):** Immutable Java Records (DTOs) mapping JSON data.
---
###  Testing

The project uses **WireMock** to simulate the TMDB API, ensuring tests run fast and without an internet connection.
```bash
# Run all tests 
./gradlew test
```
---
**Author:** [Thidas Wickramarachchi]


