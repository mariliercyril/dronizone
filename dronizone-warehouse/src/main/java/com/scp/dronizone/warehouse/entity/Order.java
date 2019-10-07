package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.states.ProcessingState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    ProcessingState processingState = ProcessingState.PENDING;
    int idOrder;
    float price;

    public Order() {
    }

    public Order(int id){
        this.idOrder = id;
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

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public ProcessingState getProcessingState(){
        return this.processingState;
    }

    public void setProcessingState(ProcessingState processingState){
        this.processingState = processingState;
    }

}
