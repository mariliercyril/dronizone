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

order_service_url="http://192.168.99.101:9001/orders/"
warehouse_service_url="http://192.168.99.101:9002/warehouse/"

curl -H "Content-Type:application/json" \
-d '{"idItem":123,"price":2.0}' \
-X POST ${warehouse_service_url}items

curl ${warehouse_service_url}items

curl -H "Content-Type:application/json" \
-d '{"idOrder":1,"price":2.0,"items":[{"idItem":123,"price":2.0}]}' \
-X POST ${order_service_url}

curl ${order_service_url}

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