# Use OpenJDK as base image
FROM eclipse-temurin:24-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/matcha-1.0-SNAPSHOT.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
