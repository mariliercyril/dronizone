package com.scp.dronizone.fleet.entity;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

public class DronePosition {
    private Long timestamp;
    private Double longitude;
    private Double latitude;

    public DronePosition() {
        this.random();
    }

    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Générer de nouvelles positions GPS
     * @return l'instance
     */
    public DronePosition random() {
        Random r = new Random();
        double u = r.nextDouble();
        double v = r.nextDouble();

        this.timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis()).getTime();
        this.latitude = Math.toDegrees(Math.acos(u*2-1)) - 90;
        this.longitude = 360 * v - 180;

        return this;
    }

    @Override
    public String toString() {
        return "[" + this.timestamp.toString() + "] "
                + "(" + this.latitude + ", " + this.longitude + ")";
    }
}