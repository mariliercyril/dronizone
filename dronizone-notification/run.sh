#!/bin/bash


chmod +x mvnw
./mvnw clean install -DskipTests
docker-compose up --build
