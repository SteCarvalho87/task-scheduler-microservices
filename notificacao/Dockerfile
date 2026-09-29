FROM gradle:8.14.3-jdk17 AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build app/build/libs/*.jar /app/notificacao.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/notificacao.jar"]