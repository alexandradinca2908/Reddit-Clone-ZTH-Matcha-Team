# Define variables
$imageName = "matcha-app"
$tag = "v1"
$dockerfilePath = "Dockerfile"
$jarFile = "target/matcha-1.0-SNAPSHOT.jar"

# Step 1: Create a Dockerfile
@"
# Use OpenJDK as base image
FROM eclipse-temurin:24-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY $jarFile app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
"@ | Set-Content -Path $dockerfilePath -Encoding UTF8

Write-Host "Dockerfile created."

# Step 2: Build the Docker image
docker build -t "${imageName}:${tag}" .

if ($?) {
    Write-Host "Docker image '${imageName}:${tag}' built successfully." -ForegroundColor Green
} else {
    Write-Host "Docker build failed." -ForegroundColor Red
}
