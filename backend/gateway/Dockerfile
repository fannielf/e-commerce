# backend/gateway/Dockerfile
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built jar file
COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar

# Copy SSL
COPY src/main/resources/ssl/keystore.p12 src/main/resources/ssl/keystore.p12

ENTRYPOINT ["java", "-jar", "app.jar"]
