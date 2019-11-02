package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.ProcessingState;

import java.util.Objects;

public class Order {
    // Askip, l'ID customer nécessaire à NotificationService sera recherché par NS quand il en aura besoin
    Long orderId;

    ProcessingState processingState = ProcessingState.PENDING;

    public Order() {
    }

    public Order(Long idOrder){
        this.orderId = idOrder;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    public ProcessingState getProcessingState(){
        return this.processingState;
    }

    public void setProcessingState(ProcessingState processingState){
        this.processingState = processingState;
    }
}
