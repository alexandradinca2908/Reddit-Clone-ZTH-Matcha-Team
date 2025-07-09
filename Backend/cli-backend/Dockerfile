# ---- STAGE 1: Build ----
# Această imagine pentru build este corectă și rămâne neschimbată
FROM maven:3.9-eclipse-temurin-24 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# ---- STAGE 2: Runtime ----
# AICI ESTE CORECȚIA: am scos "-jammy" de la final
FROM eclipse-temurin:24-jre
WORKDIR /app

# Numele JAR-ului este corect dacă ai modificat pom.xml
COPY --from=builder /app/target/matcha-1.0-SNAPSHOT.jar app.jar

# Comanda care va porni aplicația
ENTRYPOINT ["java", "-jar", "app.jar"]