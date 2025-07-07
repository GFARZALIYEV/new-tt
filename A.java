FROM gradle:7.6-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/build/libs/stp-vacancy.jar /app/stp-vacancy.jar
ENTRYPOINT ["java", "-jar", "/app/stp-vacancy.jar"]
