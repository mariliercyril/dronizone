import requests
import json
import urllib
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

# Vérification du lancement du service "warehouse"
try:
    r=requests.get(warehouse_service_url+"connected")
    print("Nouvelle article créé : " + r.text)
except:
    print("Erreur de connexion : le service est indisponible")
    sys.exit()
    

# Création d'un article
articleJson = {"idItem":123,"price":2.0}
r=requests.post(warehouse_service_url+"items",data=json.dumps(articleJson), headers=headers)
print(r.text)

# Création d'un drone
droneJson = {"batteryState":"FULL","status":"AVAILABLE"}
r=requests.post(fleet_service_url+"drones",data=json.dumps(droneJson), headers=headers)

# Création d'une commande en passant la liste d'articles qu'on veut qu'elle contienne
articleJsonList = [{"idItem":123,"price":2.0}]
print("Création d'une nouvelle commande")
r=requests.post(order_service_url,data=json.dumps(articleJsonList), headers=headers)
orderJson = json.loads(r.text) #récupération de la commande créé et enregistrer
print("Id de la nouvelle commande : " + str(orderJson["idOrder"]))
print(orderJson)

# Packing de la nouvelle commande
r=requests.put(warehouse_service_url+"orders/pack/"+str(orderJson["idOrder"]))
print("Packing de la commande : " + r.text)

# Vérification de l'état des drones
r=requests.get(fleet_service_url+"drones")
print("Drones :" + r.text)

#Vérification de l'état de la commande
r=requests.get(order_service_url+str(orderJson["idOrder"]))
print("Commandes : "+r.text)
