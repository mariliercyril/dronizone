#!/bin/bash

# This script allows us to run the 'Notification Service' and run its tests...

#cd dronizone-order
#./mvnw exec:java

#RUN SERVICES
docker run -d -p 9001:9001 -t scp1920/dronizone:order
docker run -d -p 9002:9002 -t scp1920/dronizone:warehouse
docker run -d -p 9003:9003 -t scp1920/dronizone:notification
docker run -d -p 9004:9004 -t scp1920/dronizone:fleet

sleep 10;
cd dronizone-order
mvn test
cd ..

#cd ../dronizone-warehouse
#./mvnw exec:java
sleep 10;
cd dronizone-warehouse
mvn test
cd ..

#cd ..
sleep 10;
cd dronizone-notification
gnome-terminal -- ./notification_service_tests_launcher.sh
cd ..

#cd dronizone-fleet
#./mvnw exec:java
sleep 10;
cd dronizone-notification
mvn test