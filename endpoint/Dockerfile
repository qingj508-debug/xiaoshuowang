FROM maven:3.8-openjdk-8 AS build
ARG SERVICE_NAME
WORKDIR /app
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

FROM openjdk:8-jre-alpine
ARG SERVICE_NAME
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
