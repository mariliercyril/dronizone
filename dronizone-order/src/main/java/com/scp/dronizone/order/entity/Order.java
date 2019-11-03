package com.scp.dronizone.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scp.dronizone.order.states.ProcessingState;
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

    Customer customer;
    @Field("order_status")
    ProcessingState processingState;
    @Field("order_id")
    int orderId;

    @Field("order_items")
    List<Item> items = new ArrayList<>();

    @Field("order_price")
    float price;

    public Order() {
    }

    public Order(int idOrder, float price) {
        this.orderId = idOrder;
        this.price = price;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int idOrder) {
        this.orderId = idOrder;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProcessingState getProcessingState() {
        return this.processingState;
    }

    public void setProcessingState(ProcessingState processingState) {
        this.processingState = processingState;
    }


    public void addItem(Item newItem) {
        items.add(newItem);
        this.price += newItem.getPrice();
    }

    @Override
    public String toString() {
        return "Order{" +
                "processingState=" + processingState +
                ", idOrder=" + orderId +
                ", price=" + price +
                '}';
    }
}
