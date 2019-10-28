package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.states.ProcessingState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Document(collection = "order")
public class Order {
    @Id
    String id;

    @Field("o_id")
    int idOrder;

    @Field("o_price")
    double price;

    @Field("o_status")
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
