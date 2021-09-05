# syntax=docker/dockerfile:1
FROM maven:3.6-jdk-11 AS build

COPY ./pom.xml ./pom.xml
#RUN mvn dependency:go-offline -B
RUN mvn -B dependency:resolve-plugins dependency:resolve

COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim AS runtime

COPY tools/entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]

WORKDIR /home/app
COPY --from=build target/*.jar ./app.jar

# Configure the main process to run when running the image
CMD ["java" ,"-jar", "app.jar"]
