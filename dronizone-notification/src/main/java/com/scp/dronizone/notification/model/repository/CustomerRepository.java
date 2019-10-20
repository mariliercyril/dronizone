package com.scp.dronizone.notification.model.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.stereotype.Repository;

import com.scp.dronizone.notification.model.entity.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	@Query("{ 'customer_id' : ?0 }")
	public Optional<Customer> findByCustomerId(String customerId);

}
