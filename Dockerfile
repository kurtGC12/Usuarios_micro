# ====== BUILD STAGE ======
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -DskipTests clean package

# ====== RUNTIME STAGE ======
FROM eclipse-temurin:21-jre
WORKDIR /app

# Oracle wallet (si lo necesitas en runtime)
COPY Wallet_FullStack3 ./Wallet_FullStack3
ENV TNS_ADMIN=/app/Wallet_FullStack3

# Copiar el JAR construido en el stage "build"
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
