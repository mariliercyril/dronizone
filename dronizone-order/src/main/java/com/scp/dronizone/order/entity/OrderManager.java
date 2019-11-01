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
            if (order.getIdOrder() == idOrder) {
                order.processingState = ProcessingState.PACKED;
                return;
            }
        }
    }

    public void addOrder(Order newOrder) {
        this.orderRepository.save(newOrder);
        orders.add(newOrder);
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

    public static int getNbOrder() {
        return orders.size();
    }

    public static Order createOrder(List<Item> items) {
        Order newOrder = new Order();
        float totalPrice = 0;

        for (Item item : items) {
            totalPrice += item.getPrice();
        }

        newOrder.setItems(items);
        newOrder.setPrice(totalPrice);
        newOrder.setIdOrder(OrderManager.getNbOrder() + 1);
        newOrder.processingState = ProcessingState.PENDING;

        return newOrder;
    }

    public static Order updateOrder(Order orderUpdate) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getIdOrder() == orderUpdate.getIdOrder()) {
                orders.remove(i);
                orders.add(orderUpdate);
                return orderUpdate;
            }
        }
        return null;
    }
}
