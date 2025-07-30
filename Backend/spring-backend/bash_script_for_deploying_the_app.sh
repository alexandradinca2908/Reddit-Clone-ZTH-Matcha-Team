#!/bin/bash

./mvnw clean package
scp -i ../../../MatchaKey.pem target/spring-backend-0.0.1-SNAPSHOT.jar ec2-user@13.48.209.206:~/app.jar