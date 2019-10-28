package com.scp.dronizone.notification.controller.test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.repository.OrderRepository;

/**
 * The {@code OrderControllerTest} class allows to post an order for testing...
 * 
 * @author cmarilier
 */
@RestController
public class OrderControllerTest {

	private final OrderRepository orderRepository;

	@Autowired
	public OrderControllerTest(OrderRepository orderRepository) {

		this.orderRepository = orderRepository;
	}

	@PostMapping(value = "/orders")
	public void post(@RequestBody Order order) {

		orderRepository.save(order);
	}

}
