FROM eclipse-temurin:21-ubi9-minimal AS build

RUN microdnf install -y findutils

WORKDIR /app
COPY . .

RUN ./gradlew clean bootJar


FROM eclipse-temurin:21.0.4_7-jre-ubi9-minimal

WORKDIR /app

RUN microdnf install -y shadow-utils && \
    groupadd --system appgroup && \
    useradd --system --gid appgroup --create-home appuser && \
    microdnf remove -y shadow-utils && \
    microdnf clean all
USER appuser

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]