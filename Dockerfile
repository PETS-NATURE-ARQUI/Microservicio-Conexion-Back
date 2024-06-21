
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/mi-aplicacion-0.0.1-SNAPSHOT.jar /app/mi-aplicacion.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/mi-aplicacion.jar"]