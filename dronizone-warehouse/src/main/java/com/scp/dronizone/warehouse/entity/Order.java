package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.states.ProcessingState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Order")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    int id;

    @Column(name = "o_price")
    double price;

    @Column(name = "o_status")
    ProcessingState processingState = ProcessingState.PENDING;

    public Order() {
    }

    public Order(int id){
        this.id = id;
    }

    public Order(double price){
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ProcessingState getProcessingState(){
        return this.processingState;
    }

    public void setProcessingState(ProcessingState processingState){
        this.processingState = processingState;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }


}
