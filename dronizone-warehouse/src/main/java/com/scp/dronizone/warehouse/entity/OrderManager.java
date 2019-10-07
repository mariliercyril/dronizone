package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.states.ProcessingState;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    static List<Order> orders = new ArrayList<>();

    public OrderManager() {
    }

    public static List<Order> getUnpackedOrders() {
        List<Order> unpackedOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.getProcessingState().equals(ProcessingState.PENDING))
                unpackedOrders.add(order);
        }
        return unpackedOrders; // TODO check unpackedOrders and return them
    }

    public static void setOrderPacked(int idOrder) {
        for (Order order : orders) {
            if(order.getIdOrder() == idOrder){
                order.setProcessingState(ProcessingState.PACKED);
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

    public static void resetOrders() {
        orders.clear();
    }

    public static void setOrders(List<Order> myOrders) {
        orders = myOrders;
    }

    public static int getNbOrder(){
        return orders.size();
    }
}
