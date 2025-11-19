# Use Maven with JDK 21 for building
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use smaller JRE for runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/LoginAPI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]