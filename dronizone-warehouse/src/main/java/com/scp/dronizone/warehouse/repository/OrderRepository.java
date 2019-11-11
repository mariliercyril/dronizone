package com.scp.dronizone.warehouse.repository;

import com.scp.dronizone.warehouse.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query("{ 'order_id' : ?0 }")
	public Optional<Order> findByOrderId(int orderId);

}
