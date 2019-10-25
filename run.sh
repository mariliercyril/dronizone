#!/bin/bash

# This script allows us to run the 'Notification Service' and run its tests...

#cd dronizone-order
#./mvnw exec:java

#RUN SERVICES
#docker run -d -p 9001:9001 -t scp1920/dronizone:order
#docker run -d -p 9002:9002 -t scp1920/dronizone:warehouse
#docker run -d -p 9003:9003 -t scp1920/dronizone:notification
#docker run -d -p 9004:9004 -t scp1920/dronizone:fleet
docker-compose up -d

sleep 10;

docker run --network=dronizone1920scp1920_dronizone-net scenario_1

#sleep 10;
#cd dronizone-order
#mvn -Dtest=RunCucumberTest test
#cd ..
#
#sleep 10;
#cd dronizone-warehouse
#mvn -Dtest=WarehouseServiceIntegrationTest test
#cd ..
#
#sleep 10;
#cd dronizone-notification
#mvn -Dtest=NotificationServiceTest test
#gnome-terminal -- ./notification_service_tests_launcher.sh
#cd ..
#
#sleep 10;
#cd dronizone-fleet
#mvn -Dtest=RunCucumberTest test