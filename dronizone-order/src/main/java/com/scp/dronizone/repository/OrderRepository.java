package com.scp.dronizone.repository;

import java.util.Optional;

import com.scp.dronizone.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{ 'order_id' : ?0 }")
    public Optional<Order> findByOrderId(int orderId);

}
