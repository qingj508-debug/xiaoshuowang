# Debian 底座（glibc），DNS 解析行为正常，避免 Alpine musl + JDK8 短域名解析 bug
FROM docker.m.daocloud.io/library/eclipse-temurin:8-jre
ARG SERVICE_NAME
COPY ${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
