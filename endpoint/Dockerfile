FROM eclipse-temurin:8-jre-alpine
ARG SERVICE_NAME
COPY ${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
