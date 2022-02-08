FROM openjdk:8-jdk-alpine
MAINTAINER "Jefté Luiz <jefteluiz269@gmail.com>"
WORKDIR /app

COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8081