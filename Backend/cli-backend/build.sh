#!/bin/bash

set -e

IMAGE_NAME="matcha"
IMAGE_VERSION="latest"

echo "### Pasul 1: Construirea proiectului Maven... ###"
mvn clean package

echo "### Pasul 2: Construirea imaginii Docker... ###"
docker build -t ${IMAGE_NAME}:${IMAGE_VERSION} .

echo "### Pasul 3: Salvarea imaginii Docker într-un fișier .tar... ###"
docker save -o ${IMAGE_NAME}.tar ${IMAGE_NAME}:${IMAGE_VERSION}

echo "### Pasul 4: Crearea arhivei finale... ###"
tar -czvf proiect-final.tar.gz ${IMAGE_NAME}.tar Dockerfile build.sh pom.xml src

echo "### Proces finalizat! ###"