import requests
import os
import sys

ORDER_HOST = os.environ['DRONIZONE_ORDER_SERVICE_HOST']
ORDER_PORT = os.environ['DRONIZONE_ORDER_SERVICE_PORT']

WAREHOUSE_HOST = os.environ['DRONIZONE_WAREHOUSE_SERVICE_HOST']
WAREHOUSE_PORT = os.environ['DRONIZONE_WAREHOUSE_SERVICE_PORT']

FLEET_HOST = os.environ['DRONIZONE_FLEET_SERVICE_HOST']
FLEET_PORT = os.environ['DRONIZONE_FLEET_SERVICE_PORT']

order_service_url="http://"+ORDER_HOST+":"+ORDER_PORT+"/orders/"
warehouse_service_url="http://"+WAREHOUSE_HOST+":"+WAREHOUSE_PORT+"/warehouse/"
fleet_service_url="http://"+FLEET_HOST+":"+FLEET_PORT+"/fleet/"

headers = {
    'Content-Type': 'application/json',
     'Accept': 'application/json'
}

print("status services :")
connected = False
while(not connected) :
    try:
        r=requests.get(order_service_url+"connected")
        print("order service up !")
        connected = True
    except:
        connected = False

connected = False
while(not connected) :
    try:
        r=requests.get(warehouse_service_url+"connected")
        print("warehouse service up !")
        connected = True
    except:
        connected = False

connected = False
while(not connected) :
    try:
        r=requests.get(fleet_service_url+"connected")
        print("fleet service up !")
        connected = True
    except:
        connected = False
