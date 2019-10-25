package com.scp.dronizone.order.entity;

import com.scp.dronizone.order.states.ProcessingState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    Customer customer;
    ProcessingState processingState;
    int idOrder;
    List<Item> items = new ArrayList<>();
    float price;

    public Order() {
    }

    public Order(int idOrder, float price) {
        this.idOrder = idOrder;
        this.price = price;
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

    public void addItem(Item newItem) {
        items.add(newItem);
        this.price += newItem.getPrice();
    }

    @Override
    public String toString() {
        return "Order{" +
                "processingState=" + processingState +
                ", idOrder=" + idOrder +
                ", price=" + price +
                '}';
    }
}
