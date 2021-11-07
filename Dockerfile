FROM openjdk:11.0.13-jre-slim

COPY ./release/api.jar api.jar

ENTRYPOINT ["java", "-jar", "/api.jar"]
