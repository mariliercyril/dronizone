package com.scp.dronizone.order.entity;

import com.scp.dronizone.order.states.ProcessingState;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    static List<Order> orders = new ArrayList<>();

    public OrderManager() {
    }

    public static List<Order> getUnpackedOrders() {
        List<Order> unpackedOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.processingState.equals(ProcessingState.PENDING))
                unpackedOrders.add(order);
        }
        return unpackedOrders; // TODO check unpackedOrders and return them
    }

    public static void setOrderPacked(int idOrder) {
        for (Order order : orders) {
            if(order.getIdOrder() == idOrder){
                order.processingState = ProcessingState.PACKED;
                return;
            }

        }
    }

    public static void addOrder(Order newOrder) {
        orders.add(newOrder);
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static Order getOrderById(int id) {
        for(Order order : orders){
            if(order.getIdOrder() == id)
                return order;
        }
        return null;
    }

    public static void resetOrders() {
        orders.clear();
    }

    public static void setOrders(List<Order> myOrders) {
        orders = myOrders;
    }

    public static int getNbOrder(){
        return orders.size();
    }

    public static Order createOrder(List<Item> items) {
        Order newOrder = new Order();
        float totalPrice = 0;

        for(Item item : items){
            totalPrice += item.getPrice();
        }

        newOrder.setItems(items);
        newOrder.setPrice(totalPrice);
        newOrder.setIdOrder(OrderManager.getNbOrder()+1);
        newOrder.processingState = ProcessingState.PENDING;

        return newOrder;
    }

    public static Order updateOrder(Order orderUpdate){
        for(int i=0;i<orders.size();i++){
            Order order = orders.get(i);
            if(order.getIdOrder() == orderUpdate.getIdOrder()){
                orders.remove(i);
                orders.add(orderUpdate);
                return orderUpdate;
            }
        }
        return null;
    }
}
