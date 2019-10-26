package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.DroneBatteryState;
import com.scp.dronizone.fleet.states.DroneState;

import java.util.concurrent.atomic.AtomicInteger;

public class Drone {
    private int id;

    private DroneBatteryState batteryState;

    private DroneState status;

    private Order order;

    public Drone() {
        this.id = DroneManager.counter.incrementAndGet();
        this.batteryState = DroneBatteryState.FULL;
        this.status = DroneState.AVAILABLE;
    }
    public Drone(DroneBatteryState batteryLevel, DroneState droneStatus) {
        this.id = new AtomicInteger().incrementAndGet();
        this.batteryState = batteryLevel;
        this.status = droneStatus;
    }

    /**
     * Constructeur permettant de choisir l'ID
     * À utiliser dans les tests, en attendant la BD
     *
     * @param id
     *  ID du Drone
     * @param batteryLevel
     *  Niveau de batterie du Drone
     * @param droneStatus
     *  Status du Drone
     */
    public Drone(int id, DroneBatteryState batteryLevel, DroneState droneStatus) {
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
    }

    public DroneState getStatus() {
        return status;
    }

    public void setStatus(DroneState newStatus) {
        this.status = newStatus;
    }

    /**
     * Force le Drone à faire demi tour (US#5)
     * Fonction à part car cet appel doit notifier le Client aussi (US#6)
     */
    public void forceRecall() {
        this.status = DroneState.RETURNING; // todo le Drone doit, pour l'US#6, réagir à ce
    }

    @Override
    public String toString() {
        return "Drone #" + id
                + ": \nBattery state: " + batteryState
                + "\nDrone status: " + status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
