# Imagen base 
FROM eclipse-temurin:21-jdk-jammy

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR generado por Maven
COPY target/*.jar app.jar

# Exponer puerto 
EXPOSE 8081

# Comando para ejecutar tu microservicio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]