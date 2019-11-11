#!/bin/bash

# This script allows us to prepare the environment...

chmod +x mvnw
./mvnw clean package -DskipTests

docker login -u scp1920 -p laborious
docker pull scp1920/dronizone:notification
docker pull scp1920/dronizone:order
docker pull scp1920/dronizone:warehouse
docker pull scp1920/dronizone:fleet

docker-compose -p dronizone1920scp1920 up -d

docker build -t verify_services ./demo/

docker build -t scenario_1 ./demo/scenarios/scenario_1/

docker build -t scenario_1 ./demo/scenarios/scenario_2/

echo Lancement des services...

docker run --network=dronizone1920scp1920_dronizone-net verify_services