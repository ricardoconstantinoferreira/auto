FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /workspace

# Copia apenas os arquivos necessários primeiro para aproveitar cache de build
COPY pom.xml mvnw .mvn/ ./
RUN chmod +x mvnw || true

# Copia o restante do projeto
COPY . .

# Build do projeto (pulando testes para acelerar; remova -DskipTests se quiser rodar testes durante o build)
RUN mvn -B -DskipTests clean package

# Stage final: runtime leve
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

EXPOSE 8080

# Copia o JAR gerado no estágio de build
COPY --from=build /workspace/target/auto-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
