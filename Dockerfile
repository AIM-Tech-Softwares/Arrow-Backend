FROM openjdk:21-jdk-slim  AS build

RUN apt-get update && apt-get install -y coreutils bash

WORKDIR /app
COPY ArrowCore .
RUN ./gradlew clean bootJar

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]