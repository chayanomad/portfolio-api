FROM openjdk:11.0.13-jre-slim

COPY ./build/libs/api.jar api.jar

ENTRYPOINT ["java", "-jar", "/api.jar"]
