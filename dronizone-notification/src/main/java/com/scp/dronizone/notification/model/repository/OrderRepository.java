package com.scp.dronizone.notification.model.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.stereotype.Repository;

import com.scp.dronizone.notification.model.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query("{ 'order_id' : ?0 }")
	public Optional<Order> findByOrderId(Long orderId);

}
