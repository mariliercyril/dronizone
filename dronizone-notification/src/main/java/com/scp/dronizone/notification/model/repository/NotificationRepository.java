package com.scp.dronizone.notification.model.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.stereotype.Repository;

import com.scp.dronizone.notification.model.entity.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

	@Query("{ 'order_id' : ?0 }")
	public Optional<Notification> findByOrderId(Long orderId);

}
