# syntax=docker/dockerfile:1

ARG ENV=development

FROM maven:3.6-jdk-11 AS dependency

ARG ENV
ENV ENV ${ENV}

COPY ./pom.xml ./pom.xml
RUN mvn -B dependency:resolve-plugins dependency:resolve

FROM dependency AS development
ENV MVN="mvn clean package"

FROM dependency AS production
ENV MVN="mvn clean package -DskipTests"

FROM ${ENV} AS build
COPY ./src ./src
RUN ${MVN}

FROM openjdk:11-jre-slim AS runtime

RUN apt-get update -qq && apt-get install -y postgresql-client

COPY tools/entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]

WORKDIR /home/app
COPY src/main/resources/text-files/sql/Postgres-Schema.sql .
COPY --from=build target/*.jar ./app.jar

CMD ["java" ,"-jar", "app.jar"]
