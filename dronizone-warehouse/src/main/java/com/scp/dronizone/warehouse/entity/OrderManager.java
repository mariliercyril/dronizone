package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.repository.OrderRepository;
import com.scp.dronizone.warehouse.states.ProcessingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderManager {

    @Autowired
    OrderRepository orderRepository;

    public OrderManager() {
    }

    public List<Order> getUnpackedOrders() {
        List<Order> unpackedOrders = new ArrayList<>();
        ArrayList<Order> orders = (ArrayList<Order>)orderRepository.findAll();
        for(Order order : orders){
            if(order.getProcessingState().equals(ProcessingState.PENDING))
                unpackedOrders.add(order);
        }
        return unpackedOrders; // TODO check unpackedOrders and return them
    }

    public Order setOrderPacked(int idOrder) {
        Order order = orderRepository.findByOrderId(idOrder).get();
        if(order != null){
            order.setProcessingState(ProcessingState.PACKED);
            orderRepository.save(order);
        }
        return order;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void displayPendingOrders(){
        List<Order> orders = getUnpackedOrders();
        for(Order order : orders){
            System.out.println(order.toString());
        }
    }

}
