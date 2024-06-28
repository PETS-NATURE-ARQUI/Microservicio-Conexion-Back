# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y el código fuente al contenedor
COPY pom.xml /app/
COPY src /app/src/

# Instalar Maven
RUN apk add --no-cache maven

# Construir el proyecto
RUN mvn -f /app/pom.xml clean package -DskipTests

# Copiar el archivo JAR generado al contenedor
COPY target/petsnature-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
