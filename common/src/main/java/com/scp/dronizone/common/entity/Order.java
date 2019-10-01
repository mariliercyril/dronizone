package com.scp.dronizone.common.entity;

import com.scp.dronizone.common.states.ProcessingState;

import java.util.*;

public class Order {
    Customer customer;
    ProcessingState processingState;
    String idOrder;
    List<Item> items = new ArrayList<>();
    float price;

    public Order() {
        this.idOrder = UUID.randomUUID().toString();
        this.processingState = ProcessingState.PENDING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idOrder.equals(order.idOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder);
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
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

    public void addItem(Item newItem) {
        items.add(newItem);
        this.price += newItem.getPrice();
    }

}
