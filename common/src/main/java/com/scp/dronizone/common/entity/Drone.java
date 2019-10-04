package com.scp.dronizone.common.entity;

import com.scp.dronizone.common.states.DroneBatteryState;
import com.scp.dronizone.common.states.DroneState;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private DroneBatteryState batteryState;

    private DroneState status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DroneBatteryState getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(DroneBatteryState batteryState) {
        this.batteryState = batteryState;
    }

    public DroneState getStatus() {
        return status;
    }

    public void setStatus(DroneState status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Drone #" + id
                + ": \nBattery state: " + batteryState
                + "\nDrone status: " + status;
    }
}
