package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.DroneBatteryState;
import com.scp.dronizone.fleet.states.DroneState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.MIN_PRIORITY;

public class Drone implements Runnable {
    // @Value("${fleet.service.url}")  // ne marche pas donc VERSION SALASSE
    // (on va faire semblant qu'il est important qu'il soit possible d'update l'@ FLEET_SERVICE_URL des Drones via une requête :^)))))))))))))))))))))))
    public String FLEET_SERVICE_URL;// = "localhost:9004";

    /**
     * Identifiant unique du Drone
     */
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
        this.id = DroneManager.counter.incrementAndGet();
        this.batteryState = DroneBatteryState.FULL;
        this.status = DroneState.AVAILABLE;
        this.order = null;
        this.position = new DronePosition();
    }
    public Drone(DroneBatteryState batteryLevel, DroneState droneStatus) {
        this();
        this.id = new AtomicInteger().incrementAndGet();
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
        this();
        this.id = id;
        this.batteryState = batteryLevel;
        this.status = droneStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    // fusionner avec le setter ? potentiellement ok car pas de modification des données ("you get what you set")
    /**
     * Affecter un colis (Order) au Drone
     * Ceci a pour effet de l'expédier (DELIVERING)
     * @param assignedOrder ID de l'Order affecté au Drone
     */
    public void assignOrder(Order assignedOrder) {
        this.setOrder(assignedOrder);
        this.updateStatus(DroneState.DELIVERING);
        // todo thread à part pour "livrer" (5 changements de positions,
        //  à 3/5 -> informer que le Drone arrive bientôt
        //  à 5/5 -> livraison terminée => AVAILABLE (BATTERY--)

        System.out.println("Drone #" + this.id + "'s FLEET_SERVICE_URL -> " + FLEET_SERVICE_URL );
        System.out.println("Drone #" + this.id + "'s FLEET_SERVICE_URL -> " + FLEET_SERVICE_URL );
        System.out.println("Drone #" + this.id + "'s FLEET_SERVICE_URL -> " + FLEET_SERVICE_URL );
        System.out.println("Drone #" + this.id + "'s FLEET_SERVICE_URL -> " + FLEET_SERVICE_URL );

        new Thread(this).start();
    }

    @Override
    public String toString() {
        return "Drone #" + id
                + ": \nBattery state: " + batteryState
                + "\nDrone status: " + status;
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
        while (progress-- > 0 && !Thread.currentThread().isInterrupted() && this.status!=DroneState.FORCED_RETURNING) {
            try {
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
    }

    /**
     * US#8-9 - Telemetry
     * todo reproduire le test de PostMan
     */
    public void postPositionToFleetService() {
        String url = "http://" + FLEET_SERVICE_URL + "/fleet/drones/" + this.id + "/positions";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, this.position, String.class);
    }

    public void postNotificationToFleetService(Notification notification) {
        RestTemplate restTemplate = new RestTemplate();
        String postNotifToFleetUrl = "http://" + FLEET_SERVICE_URL + "/fleet/notifications";
        System.out.println("postNotifToFleetUrl: \n\n\n" + postNotifToFleetUrl + "\n\n\n");

        ResponseEntity responseEntity = restTemplate.postForEntity(postNotifToFleetUrl, notification, String.class);

        System.out.println("responseEntity: " + responseEntity);
        System.out.println("responseEntity.getStatusCode(): " + responseEntity.getStatusCode());
    }
}
