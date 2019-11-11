package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.DroneBatteryState;
import com.scp.dronizone.fleet.states.DroneState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.MIN_PRIORITY;

@Document(collection = "drone")
public class Drone implements Runnable {
    // @Value("${fleet.service.url}")  // ne marche pas donc VERSION SALASSE
    // (on va faire semblant qu'il est important qu'il soit possible d'update l'@ FLEET_SERVICE_URL des Drones via une requête :^)))))))))))))))))))))))
    public String FLEET_SERVICE_URL;// = "localhost:9004";

    @Id
    private String mongoId;

    /**
     * Identifiant unique du Drone
     */
    @Field("drone_id")
    private int id;

    /**
     * Niveau de batterie du Drone
     * (utilisé pour déterminer si un Drone doit se recharger ou non)
     */
    private DroneBatteryState batteryState;

    /**
     * Disponibilité du Drone (dispo/recharge/livraison/retour de livraison)
     */
    private volatile DroneState status;

    private Order order;

    private DronePosition position;


    public Drone() {
        this(69); // Nice.
    }

    public Drone(int id) {
        this.id = id;
        this.batteryState = DroneBatteryState.FULL;
        this.status = DroneState.AVAILABLE;
        this.order = null;
        this.position = new DronePosition(id);
        this.position.setDroneId(id);
    }
    public Drone(DroneBatteryState batteryLevel, DroneState droneStatus) {
        //this();
        this.batteryState = batteryLevel;
        this.status = droneStatus;
    }

    /**
     * Constructeur permettant de choisir l'ID
     * À utiliser dans les tests, en attendant la BD
     *
     * @param id ID du Drone
     * @param batteryLevel Niveau de batterie du Drone
     * @param droneStatus Status du Drone
     */
    public Drone(int id, DroneBatteryState batteryLevel, DroneState droneStatus) {
        this(id);
        this.batteryState = batteryLevel;
        this.status = droneStatus;
        this.position = new DronePosition(id);
        this.position.setDroneId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        position.setDroneId(id);
    }

    public DroneBatteryState getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(DroneBatteryState newBatteryState) {
        this.batteryState = newBatteryState;
        // todo quitter l'état RECHARGING quand on devient full
    }

    public DroneState getStatus() {
        return status;
    }

    public void setStatus(DroneState newStatus) {
        this.status = newStatus;
    }
    /**
     * Changer le status du Drone
     * Changer le status vers RECHARGING a pour effet de recharger 1 "niveau" de batterie du Drone
     * @param newStatus nouveau status à affecter au Drone
     */
    public void updateStatus(DroneState newStatus) {
        this.status = newStatus;
        // RETURNING est réservé à la fin d'une livraison. De ce fait, l'ID de colis assigné au Drone est supprimé
        if (newStatus == DroneState.AVAILABLE)
            this.order = null;
        // RECHARGING fait remonter de "1" le niveau de batterie du Drone (du coup c'est instant, ce qui est... meh)
        if (newStatus == DroneState.RECHARGING)
            switch (this.batteryState) {
                case EMPTY:
                    this.setBatteryState(DroneBatteryState.LOW);
                    break;
                case LOW:
                    this.setBatteryState(DroneBatteryState.OK);
                    break;
                case OK:
                    this.setBatteryState(DroneBatteryState.FULL);
                    break;
                default:
                    break;
            }
        // todo récusivité jusqu'à devenir FULL ? Meh, c'est surement Elena qui mérite son smic en s'occupant de ça...
    }
    /**
     * Force le Drone à faire demi tour (US#5)
     * Fonction à part car cet appel doit notifier le Client aussi (US#6)
     */
    public void forceRecall() {
        this.status = DroneState.FORCED_RETURNING; // todo le Drone doit, pour l'US#6, réagir à ce
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getFLEET_SERVICE_URL() {
        return this.FLEET_SERVICE_URL;
    }

    public void setFLEET_SERVICE_URL(String FLEET_SERVICE_URL) {
        this.FLEET_SERVICE_URL = FLEET_SERVICE_URL;
    }

    public DronePosition getPosition() {
        return position;
    }

    public void setPosition(DronePosition position) {
        this.position = position;
    }

    /**
     * Affecter un colis (Order) au Drone
     * Ceci a pour effet de l'expédier (DELIVERING)
     * @param assignedOrder ID de l'Order affecté au Drone
     */
    public void assignOrder(Order assignedOrder) {
        this.setOrder(assignedOrder);
        this.updateStatus(DroneState.DELIVERING);

        new Thread(this).start();
    }

    @Override
    public String toString() {
        return "Drone #" + id
                + ": Battery state: " + batteryState
                + ", Status: " + status;
    }

    /**
     * Execution asynchrone de la livraison
     */
    @Override
    public void run() {
        Thread.currentThread().setPriority(MIN_PRIORITY);
        int progress = 5;
        // todo check Thread.currentThread().isInterrupted() pour arrêter la livraison en cas de retour forcé
        // pas sûr le Thread est réellement KILL, il me semble que oui... (car la variable volatile sert à ça. Ce n'est pas une "pause" mais bien une "fin")
        // continue processing
        while (progress-- > 0 && !Thread.currentThread().isInterrupted() && this.status != DroneState.FORCED_RETURNING) {
            try {
                // Maintenant  qu'on est en mode BD, le Drone en cours de livraison doit explicitement demander à la BD son "status"
                // ("est-ce que je suis en "FORCED_RETURNING")
                // Sinon il faut conserver une références aux Drones en cours de livraison dans DroneManager
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://" + FLEET_SERVICE_URL + "/fleet/drones/" + this.id;
                this.updateStatus(restTemplate.getForEntity(url, Drone.class).getBody().getStatus());
                System.out.println("Drone #" + id + "status " + status);

                if (this.status == DroneState.FORCED_RETURNING)
                    break;

                // Nouvelles coordonnées
                this.position.random();
                this.postPositionToFleetService();
                System.out.println("Étape " + (5-progress) + "/5 de la livraison du drone #" + this.id);
                System.out.println("Le drone #" + this.id + " s'est déplacé à " + this.position);

                // Informer de l'arrivé todo impl notification
                if (progress == 1) {
                    // Envoyer la requête de Notification TYPE == WILL_SHORTLY_BE_DELIVERED
                    postNotificationToFleetService(new Notification(order.orderId, Notification.Type.WILL_SHORTLY_BE_DELIVERED));
                }

                // 1 seconde entre deux déplacements
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // good practice askip
                Thread.currentThread().interrupt();
                return;
            }
        }

        // send "forced return" notification
        if (this.status == DroneState.FORCED_RETURNING) {
            postNotificationToFleetService(new Notification(order.orderId, Notification.Type.WILL_FINALLY_NOT_BE_DELIVERED));
        } else {
            // Livraison terminée
            this.setOrder(null);
            this.updateStatus(DroneState.AVAILABLE);
        }
        // MàJ la BD
        postUpdatedPropertiesToFleetService();
    }

    /**
     * US#8-9 - Telemetry
     */
    private void postPositionToFleetService() {
        String url = "http://" + FLEET_SERVICE_URL + "/fleet/drones/" + this.id + "/positions";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, this.position, String.class);
    }

    /**
     * Envoyer une Notification à FleetService pour être envoyée au destinataire
     *
     * @param notification Notification à envoyer
     */
    private void postNotificationToFleetService(Notification notification) {
        RestTemplate restTemplate = new RestTemplate();
        String postNotifToFleetUrl = "http://" + FLEET_SERVICE_URL + "/fleet/notifications";
        System.out.println("Le Drone #" + id + " envoie une notification '" + notification.getType() + "'");

        try {
            ResponseEntity responseEntity = restTemplate.postForEntity(postNotifToFleetUrl, notification, String.class);
        } catch (Exception e) {
            System.out.println("Il y a eu un problème lors de la transmission de la notification.\n" + e.getMessage() );
        }
    }

    /**
     * Envoyer à FleetService ses nouvelles valeurs d'attributs pour MàJ la BD
     */
    private void postUpdatedPropertiesToFleetService() {
        RestTemplate restTemplate = new RestTemplate();
        String putUpdateDroneToFleetUrl = "http://" + FLEET_SERVICE_URL + "/fleet/drones/" + this.id;

        restTemplate.put(putUpdateDroneToFleetUrl, this, String.class);
    }
}
