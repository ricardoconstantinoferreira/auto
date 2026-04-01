# Auto Rental Backend

<!-- Badges das tecnologias -->

<p align="left">
  <a href="https://www.java.com/">
    <img alt="Java" src="https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white" />
  </a>
  <a href="https://spring.io/projects/spring-boot">
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen?logo=spring&logoColor=white" />
  </a>
  <a href="https://www.postgresql.org/">
    <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-%234169e1?logo=postgresql&logoColor=white" />
  </a>
  <a href="#">
    <img alt="AI" src="https://img.shields.io/badge/AI-enabled-orange?logo=ai&logoColor=white" />
  </a>
</p>

## Description
This project is a backend application for a vehicle rental service. It provides APIs for managing vehicles, customers, reservations and notifications, and integrates AI capabilities for model-based features.

## Technologies
- Java
- Spring Boot
- PostgreSQL
- Ollama (local LLM runtime)
- AI integrations (LLM-based features)

## Features
- Vehicle and reservation management
- Customer management
- Email notifications
- AI-assisted features via Ollama

## Requirements
- JDK 17+
- Maven
- PostgreSQL
- Ollama installed and running (for local model serving)

## Configuration
Important configuration values (example `application.yml` / environment variables):
- `spring.datasource.url` — PostgreSQL JDBC URL
- `spring.datasource.username`
- `spring.datasource.password`
- `ollama.endpoint` — Ollama server URL (if needed)
- Any AI/model specific keys or settings

## Running
1. Configure PostgreSQL and set database credentials.
2. Start Ollama and load the required model(s).
3. Build and run:
    - `mvn clean package`
    - `java -jar target/your-artifact.jar`

## Testing
- Run unit and integration tests with Maven:
    - `mvn test`

## Notes
- AI features use Ollama for local model inference. Ensure models are available before using AI endpoints.
- Database migrations and further environment setup may be required depending on deployment.

## License
See project license file.

## Link Project
www.ferreira-auto.com.br
