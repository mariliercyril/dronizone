package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.repository.OrderRepository;
import com.scp.dronizone.warehouse.states.ProcessingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderManager {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MongoTemplate mongoTemplate;

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
            Query query = new Query();
            query.addCriteria(Criteria.where("order_id").is(idOrder));
            Update update = new Update();
            update.set("order_status", ProcessingState.PACKED);
            mongoTemplate.updateFirst(query, update, Order.class);

        }
        return orderRepository.findByOrderId(idOrder).get();
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
