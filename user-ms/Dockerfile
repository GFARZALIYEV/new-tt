FROM gradle:7.6-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon


FROM openjdk:17
COPY build/libs/user-ms-1.0-SNAPSHOT.jar /app/user-ms.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app/user-ms.jar"]