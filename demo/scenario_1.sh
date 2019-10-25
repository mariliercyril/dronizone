#!/bin/bash

order_service_url="http://localhost:9001/orders/"
warehouse_service_url="http://localhost:9002/warehouse/"

#until $(curl --output /dev/null --silent --head --fail http://myhost:warehouse_service_url+"connected"); do
#    printf 'connecting...'
#    printf 'retry...'
#    sleep 5
#done

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