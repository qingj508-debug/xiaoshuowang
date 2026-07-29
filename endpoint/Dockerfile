# Debian 底座（glibc），DNS 解析行为正常；基础镜像统一从私有 Harbor 拉取，不依赖外网镜像源
FROM 192.168.10.102/xiaoshuowang/eclipse-temurin:8-jre
ARG SERVICE_NAME
COPY ${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
