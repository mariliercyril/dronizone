package com.scp.dronizone.notification.controller;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;

import org.springframework.test.context.ContextConfiguration;

import org.springframework.web.client.RestTemplate;

import com.scp.dronizone.notification.NotificationService;

import com.scp.dronizone.notification.dao.entity.Customer;
import com.scp.dronizone.notification.dao.entity.CustomerOrder;
import com.scp.dronizone.notification.dao.entity.CustomerOrderRepository;
import com.scp.dronizone.notification.dao.entity.CustomerRepository;

/**
 * The {@code NotificationControllerTest} class allows to test notifications (by a drone) to a customer.
 * 
 * @author cmarilier
 */
@ContextConfiguration(classes=NotificationService.class)
@SpringBootTest
public class NotificationControllerTest {

	private static final String NOTIFICATION_SERVICE_URL = "http://localhost:9003/drone/delivery/notification";

	protected ResponseEntity<?> responseEntity = null;

	protected RestTemplate restTemplate = null;

	protected Customer customer;
	protected CustomerOrder customerOrder;

	protected CustomerOrderRepository customerOrderRepository;

	protected CustomerRepository customerRepository;

	/**
	 * Tests the consumption of the notification service...
	 * 
	 * @param idOrder
	 *  the order ID
	 */
	protected void consumeNotificationService(String route, String notification) {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		responseEntity = restTemplate.postForEntity(NOTIFICATION_SERVICE_URL + route, notification, String.class);
	}

}
