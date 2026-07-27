FROM maven:3.8-eclipse-temurin-8 AS build
ARG SERVICE_NAME
ARG http_proxy
ARG https_proxy
ENV http_proxy=$http_proxy
ENV https_proxy=$https_proxy
WORKDIR /app

# 配置 Maven 代理 + HTTP 镜像
RUN mkdir -p /root/.m2 && echo '<?xml version="1.0" encoding="UTF-8"?>\
<settings>\
<mirrors><mirror>\
<id>aliyun</id><mirrorOf>*</mirrorOf>\
<url>http://maven.aliyun.com/nexus/content/groups/public</url>\
</mirror></mirrors>\
<proxies><proxy>\
<id>clash</id><active>true</active><protocol>http</protocol>\
<host>192.168.10.1</host><port>7897</port>\
<nonProxyHosts>localhost|127.0.0.1|192.168.*</nonProxyHosts>\
</proxy></proxies>\
</settings>' > /root/.m2/settings.xml

COPY pom.xml .
COPY endpoint-common/pom.xml endpoint-common/
COPY endpoint-book/pom.xml endpoint-book/
COPY endpoint-author/pom.xml endpoint-author/
COPY endpoint-member/pom.xml endpoint-member/
COPY endpoint-home/pom.xml endpoint-home/
COPY endpoint-search/pom.xml endpoint-search/
COPY endpoint-third-party/pom.xml endpoint-third-party/
COPY endpoint-gateway/pom.xml endpoint-gateway/
RUN mvn dependency:go-offline -pl endpoint-common -B -q
COPY . .
RUN mvn package -DskipTests -pl ${SERVICE_NAME} -am -q

FROM eclipse-temurin:8-jre-alpine
ARG SERVICE_NAME
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
