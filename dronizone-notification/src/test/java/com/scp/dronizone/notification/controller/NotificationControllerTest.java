package com.scp.dronizone.notification.controller;

import org.junit.jupiter.api.BeforeEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Order;

/**
 * The {@code NotificationControllerTest} class allows to test notifications (by a drone) to a customer.
 * 
 * @author cmarilier
 */
@SpringBootTest
public class NotificationControllerTest {

	@Value("${server.host}")
	private static String host;

	@Value("${server.port}")
	private static String port;

	private static final UriComponentsBuilder URI_COMPONENTS_BUILDER = UriComponentsBuilder.fromHttpUrl("http://" + host + ":" + port);
	private static final String URI = URI_COMPONENTS_BUILDER.toUriString();

	@Autowired
	protected RestTemplate restTemplate = null;

	@BeforeEach
	public String ping() {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<Boolean> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, httpEntity, Boolean.class);

		return String.valueOf(responseEntity.getStatusCodeValue()) + " " + responseEntity.getStatusCode().getReasonPhrase();
	}

	protected Customer customer;
	protected Order order;

	/**
	 * Tests the consumption of the notification service...
	 * 
	 * @param idOrder
	 *  the order ID
	 */
	protected ResponseEntity<?> notifyThatOrderWillShortlyBeDelivered(String route, String orderId) {

		return restTemplate.patchForObject(URI + route, null, ResponseEntity.class, orderId);
	}

	/**
	 * Tests the consumption of the notification service...
	 * 
	 * @param idOrder
	 *  the order ID
	 */
	protected ResponseEntity<?> notifyThatOrderWillFinallyNotBeDelivered(String route, String orderId, String reason) {

		return restTemplate.patchForObject(URI + route, reason, ResponseEntity.class, orderId);
	}

}
