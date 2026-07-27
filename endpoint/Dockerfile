FROM maven:3.8-eclipse-temurin-8 AS build
ARG SERVICE_NAME
ARG http_proxy
ARG https_proxy
ARG no_proxy
ENV http_proxy=$http_proxy
ENV https_proxy=$https_proxy
ENV no_proxy=$no_proxy,maven.aliyun.com
WORKDIR /app

# 配置阿里云 Maven 镜像（国内直连，不走代理）
RUN mkdir -p /root/.m2 && echo '<?xml version="1.0" encoding="UTF-8"?>\
<settings>\
<mirrors><mirror>\
<id>aliyun</id><mirrorOf>*</mirrorOf>\
<url>https://maven.aliyun.com/repository/public</url>\
</mirror></mirrors>\
</settings>' > /root/.m2/settings.xml

# 禁用 wagon 连接池 + 失败重试，避免长时间下载时复用过期 TLS 连接导致 SSL 握手失败
ENV MAVEN_ARGS="-Dmaven.wagon.http.pool=false -Dmaven.wagon.http.retryHandler.count=5 -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 -Dhttps.protocols=TLSv1.2"

COPY pom.xml .
COPY endpoint-common/pom.xml endpoint-common/
COPY endpoint-book/pom.xml endpoint-book/
COPY endpoint-author/pom.xml endpoint-author/
COPY endpoint-member/pom.xml endpoint-member/
COPY endpoint-home/pom.xml endpoint-home/
COPY endpoint-search/pom.xml endpoint-search/
COPY endpoint-third-party/pom.xml endpoint-third-party/
COPY endpoint-gateway/pom.xml endpoint-gateway/
# mvn 失败自动重试（每次重跑从已下载缓存继续），对抗间歇性网络掩断
RUN for i in 1 2 3 4 5; do \
      mvn dependency:go-offline -pl endpoint-common -B -q $MAVEN_ARGS && break; \
      [ $i -eq 5 ] && exit 1; \
      echo "== attempt $i failed, retrying =="; sleep 5; \
    done
COPY . .
RUN for i in 1 2 3 4 5; do \
      mvn package -DskipTests -pl ${SERVICE_NAME} -am -q $MAVEN_ARGS && break; \
      [ $i -eq 5 ] && exit 1; \
      echo "== attempt $i failed, retrying =="; sleep 5; \
    done

FROM eclipse-temurin:8-jre-alpine
ARG SERVICE_NAME
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
