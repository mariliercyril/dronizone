package com.scp.dronizone.notification.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Notification;
import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.repository.CustomerRepository;
import com.scp.dronizone.notification.model.repository.NotificationRepository;
import com.scp.dronizone.notification.model.repository.OrderRepository;

import com.scp.dronizone.notification.service.INotificationService;

/**
 * The {@code NotificationController} class allows to send a notification...
 * 
 * @author cmarilier
 */
@RestController
public class NotificationController implements INotificationService {

	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final NotificationRepository notificationRepository;

	@Autowired
	public NotificationController(OrderRepository orderRepository, CustomerRepository customerRepository, NotificationRepository notificationRepository) {

		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.notificationRepository = notificationRepository;
	}

	@Override
	@PostMapping(value = "/notifications")
	public ResponseEntity<String> send(@RequestBody Notification notification) {
		System.out.println("Notification " + notification.getType() + " received for Order #" + notification.getOrderId());

		Long orderId = notification.getOrderId();

		Optional<Order> optionalOrder = (orderRepository.findByOrderId(orderId));
		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			Long customerId = order.getCustomerId();
			Optional<Customer> optionalCustomer = (customerRepository.findByCustomerId(customerId));
			if (optionalCustomer.isPresent()) {
				Customer customer = optionalCustomer.get();
				String messageHead = String.format(Notification.MESSAGE_HEAD_FORMAT, (customer.getGender()).getAbbreviation(), customer.getName());
				String messageBody = new String();
				switch(notification.getType()) {
				case WILL_SHORTLY_BE_DELIVERED:
					messageBody = String.format((notification.getType()).getMessageBodyFormat(), orderId, order.getDeliveryAddress());
					break;
				case WILL_FINALLY_NOT_BE_DELIVERED:
					messageBody = String.format((notification.getType()).getMessageBodyFormat(), notification.getReason(), orderId);
					break;
				}
				String message = new String(messageHead + " " + messageBody + ".");
				notification.setMessage(message);
				notificationRepository.save(notification);

				return new ResponseEntity<String>("Notification message: \"" + message + "\"", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>("No customer found with ID " + customerId + ".", HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<String>("No order found with ID " + orderId + ".", HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(value = "/notifications/{id}")
	public ResponseEntity<String> getNotificationByOrderId(@PathVariable("id") Long orderId) {

		Optional<Notification> optionalNotification = notificationRepository.findByOrderId(orderId);
		if (optionalNotification.isPresent()) {
			Notification notification = optionalNotification.get();

			return new ResponseEntity<String>(notification.getMessage(), HttpStatus.OK);
		}

		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

}
