import requests
import json
import urllib
import os 

ORDER_HOST = os.environ['DRONIZONE_ORDER_SERVICE_HOST']
ORDER_PORT = os.environ['DRONIZONE_ORDER_SERVICE_PORT']

WAREHOUSE_HOST = os.environ['DRONIZONE_WAREHOUSE_SERVICE_HOST']
WAREHOUSE_PORT = os.environ['DRONIZONE_WAREHOUSE_SERVICE_PORT']

FLEET_HOST = os.environ['DRONIZONE_FLEET_SERVICE_HOST']
FLEET_PORT = os.environ['DRONIZONE_FLEET_SERVICE_PORT']

order_service_url="http://"+ORDER_HOST+":"+ORDER_PORT+"/orders/"
warehouse_service_url="http://"+WAREHOUSE_HOST+":"+WAREHOUSE_PORT+"/warehouse/"

headers = {
    'Content-Type': 'application/json',
     'Accept': 'application/json'
}

r=requests.get(warehouse_service_url+"connected")
print(r.text)

articleJson = {"idItem":123,"price":2.0}
r=requests.post(warehouse_service_url+"items",data=json.dumps(articleJson), headers=headers)
print(r.text)

orderJson = {"idOrder":2,"price":2.0,"processingState":"PENDING","items":[{"idItem":123,"price":2.0}]}
r=requests.post(order_service_url,data=json.dumps(orderJson), headers=headers)
print(r.text)

r=requests.put(warehouse_service_url+"orders/pack/"+str(orderJson["idOrder"]))
print(r.text)
