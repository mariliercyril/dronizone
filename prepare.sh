#!/bin/bash

# This script allows us to prepare the environment...

chmod +x mvnw
./mvnw clean package -DskipTests

docker login -u scp1920 -p laborious
docker pull scp1920/dronizone:notification
docker pull scp1920/dronizone:order
