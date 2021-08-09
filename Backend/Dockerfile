# syntax=docker/dockerfile:1
FROM maven:3.6-jdk-11 AS build

# Prepare working directory
WORKDIR /build
COPY src src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim AS runtime

# Add a script to be executed every time the container starts.
COPY tools/entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]

WORKDIR /app
#COPY --from=build /build/target .
ARG JAR_FILE=build/target/*.jar
COPY --from=build ${JAR_FILE} app.jar

# Configure the main process to run when running the image
CMD ["java" ,"-jar", "app.jar"]
