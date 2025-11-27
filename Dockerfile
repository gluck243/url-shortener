FROM eclipse-temurin:21-jdk-alpine
LABEL authors="anton"

WORKDIR /app

COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]