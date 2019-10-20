package com.scp.dronizone.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Notification;
import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.entity.exception.OrderNotFoundException;

import com.scp.dronizone.notification.model.repository.CustomerRepository;
import com.scp.dronizone.notification.model.repository.OrderRepository;

import com.scp.dronizone.notification.service.INotificationService;

/**
 * The {@code NotificationController} class allows to notify a customer.
 * 
 * @author cmarilier
 */
@RestController
public class NotificationController implements INotificationService {

	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;

	@Autowired
	public NotificationController(OrderRepository orderRepository, CustomerRepository customerRepository) {

		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	@PatchMapping(value = "/orders/{id}/will-shortly-be-delivered")
	public ResponseEntity<Notification> notifyThatOrderWillShortlyBeDelivered(@PathVariable String orderId) {

		Order order = (orderRepository.findByOrderId(orderId)).orElseThrow(() -> new OrderNotFoundException(orderId));

		String customerId = order.getCustomerId();
		Customer customer = (customerRepository.findByCustomerId(customerId)).orElseThrow(() -> new OrderNotFoundException(customerId));
		String messageHead = String.format(Notification.MESSAGE_HEAD_FORMAT, (customer.getGender()).getAbbreviation(), customer.getName());

		String messageBody = String.format((Notification.Type.WILL_SHORTLY_BE_DELIVERED).getMessageFormat(), orderId, order.getDeliveryAddress());

		Notification notification = new Notification(messageHead + " " + messageBody + "...");

		order.addNotification(notification);
		orderRepository.save(order);

		return new ResponseEntity<Notification>(HttpStatus.OK);
	}

	@Override
	@PatchMapping(value = "/orders/{id}/will-finally-not-be-delivered")
	public ResponseEntity<Notification> notifyThatOrderWillFinallyNotBeDelivered(@RequestBody String reason, @PathVariable String orderId) {

		Order order = (orderRepository.findByOrderId(orderId)).orElseThrow(() -> new OrderNotFoundException(orderId));

		String customerId = order.getCustomerId();
		Customer customer = (customerRepository.findByCustomerId(customerId)).orElseThrow(() -> new OrderNotFoundException(customerId));
		String messageHead = String.format(Notification.MESSAGE_HEAD_FORMAT, (customer.getGender()).getAbbreviation(), customer.getName());

		String messageBody = String.format((Notification.Type.WILL_FINALLY_NOT_BE_DELIVERED).getMessageFormat(), reason, orderId);

		Notification notification = new Notification(messageHead + " " + messageBody + ".");

		order.addNotification(notification);
		orderRepository.save(order);

		return new ResponseEntity<Notification>(HttpStatus.OK);
	}

}
