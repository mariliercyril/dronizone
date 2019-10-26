package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.ProcessingState;

import java.util.Objects;

public class Order {

    int idOrder;

    double price;

    ProcessingState processingState = ProcessingState.PENDING;

    public Order() {
    }

    public Order(int idOrder){
        this.idOrder = idOrder;
    }

    public Order(double price){
        this.price = price;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idOrder == order.idOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder);
    }

    public ProcessingState getProcessingState(){
        return this.processingState;
    }

    public void setProcessingState(ProcessingState processingState){
        this.processingState = processingState;
    }

    public double getPrice() {
        return price;
    }


}
