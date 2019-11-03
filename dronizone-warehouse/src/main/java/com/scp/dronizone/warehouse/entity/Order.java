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

    @Field("order_id")
    int orderId;

    @Field("order_price")
    double price;

    @Field("order_status")
    ProcessingState processingState = ProcessingState.PENDING;

    public Order() {
    }

    public Order(int idOrder){
        this.orderId = idOrder;
    }

    public Order(double price){
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int idOrder) {
        this.orderId = idOrder;
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

    public double getPrice() {
        return price;
    }


}
