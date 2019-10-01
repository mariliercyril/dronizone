package com.scp.dronizone.common.entity;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    List<Order> orders;

    public OrderManager() {
        orders = new ArrayList<>();
    }

    public OrderManager(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getUnpackedOrders() {
        return null; // TODO check unpackedOrders and return them
    }

    public void setOrderPacked(int idOrder) {

    }

    public void addOrder(Order newOrder) {
        this.orders.add(newOrder);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
