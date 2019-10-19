#!/bin/bash

# This script allows us to run the 'Notification Service' and run its tests...

#cd dronizone-order
#./mvnw exec:java
docker run -d -p 9003:9003 -t scp1920/dronizone:notification
sleep 10;
cd dronizone-order
mvn test


#cd ../dronizone-warehouse
#./mvnw exec:java

#cd ..
docker run -d -p 9003:9003 -t scp1920/dronizone:notification
sleep 10;
cd dronizone-notification
gnome-terminal -- ./notification_service_tests_launcher.sh

#cd dronizone-fleet
#./mvnw exec:java
