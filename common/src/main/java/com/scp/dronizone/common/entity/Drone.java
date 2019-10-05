package com.scp.dronizone.common.entity;

import com.scp.dronizone.common.states.DroneBatteryState;
import com.scp.dronizone.common.states.DroneState;

import java.util.concurrent.atomic.AtomicInteger;

public class Drone {
    private int id;

    private DroneBatteryState batteryState;

    private DroneState status;

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

    @Override
    public String toString() {
        return "Drone #" + id
                + ": \nBattery state: " + batteryState
                + "\nDrone status: " + status;
    }
}
