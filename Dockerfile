FROM openjdk:17
COPY build/libs/stp-vacancy.jar /app/stp-vacancy.jar
ENTRYPOINT ["java", "-jar", "/app/stp-vacancy.jar"]
