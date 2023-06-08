FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR .
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package

FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY --from=builder ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
