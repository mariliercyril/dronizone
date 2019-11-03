package com.scp.dronizone.order.entity;

import com.scp.dronizone.order.states.ProcessingState;
import com.scp.dronizone.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderManager {
    static List<Order> orders = new ArrayList<>();

    @Autowired
    private final OrderRepository orderRepository;

    public OrderManager(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public static List<Order> getUnpackedOrders() {
        List<Order> unpackedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.processingState.equals(ProcessingState.PENDING))
                unpackedOrders.add(order);
        }
        return unpackedOrders; // TODO check unpackedOrders and return them
    }

    public static void setOrderPacked(int idOrder) {
        for (Order order : orders) {
            if (order.getOrderId() == idOrder) {
                order.processingState = ProcessingState.PACKED;
                return;
            }
        }
    }

    public Order addOrder(Order newOrder) {
        if(newOrder.getOrderId() == 0){
            newOrder.setOrderId((int)this.getNbOrder()+1);
        }else{
            Optional<Order> optionalOrder = this.orderRepository.findByOrderId(newOrder.getOrderId());
            if(optionalOrder.isPresent()){
                return null;
            }
        }

        this.orderRepository.save(newOrder);
        orders.add(newOrder);
        return newOrder;
    }

    public List<Order> getOrders() {
        List<Order> orders = this.orderRepository.findAll();
        if (orders != null) {
            return orders;
        }
        return new ArrayList<Order>();
    }

    public Order getOrderById(int id) {
        Optional<Order> order = orderRepository.findByOrderId(id);
        if (order.isPresent()) {
            return order.get();
        }
        return null;
//        for (Order order : orders) {
//            if (order.getIdOrder() == id)
//                return order;
//        }
    }

    public void resetOrders() {
        orderRepository.deleteAll();
    }

    public static void setOrders(List<Order> myOrders) {
        orders = myOrders;
    }

    public long getNbOrder() {
        return orderRepository.count();
    }

    public Order createOrder(List<Item> items) {
        Order newOrder = new Order();
        float totalPrice = 0;

        for (Item item : items) {
            totalPrice += item.getPrice();
        }

        newOrder.setItems(items);
        newOrder.setPrice(totalPrice);
        newOrder.setOrderId((int) getNbOrder() + 1);
        newOrder.processingState = ProcessingState.PENDING;

        return newOrder;
    }

    public Order updateOrder(Order orderUpdate) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderUpdate.getOrderId());
        if(optionalOrder.isPresent()){

            this.orderRepository.save(orderUpdate);
            return orderUpdate;
        }
        return null;
    }
}
