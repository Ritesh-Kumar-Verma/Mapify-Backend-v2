# Stage 1: Build with Maven
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first (for caching dependencies)
COPY pom.xml .
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests -U

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (optional; Render uses $PORT)
EXPOSE 8080

# Run the Spring Boot app on Render’s assigned port
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
