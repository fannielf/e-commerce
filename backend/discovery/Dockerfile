# backend/discovery/Dockerfile
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built jar file
COPY target/discovery-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
