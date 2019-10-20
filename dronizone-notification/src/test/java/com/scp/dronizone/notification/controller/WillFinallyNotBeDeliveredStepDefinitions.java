package com.scp.dronizone.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import com.scp.dronizone.notification.model.entity.Customer;
import com.scp.dronizone.notification.model.entity.Order;

import com.scp.dronizone.notification.model.repository.CustomerRepository;
import com.scp.dronizone.notification.model.repository.OrderRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

/**
 * The {@code WillFinallyNotBeDeliveredStepDefinitions} class defines the Gherkin steps (in
 * the scenarios of the Cucumber <i>feature</i> "Does the drone assigned to our delivery
 * notify us when it is no longer able to fulfill its mission?" [USER STORY 6]) as Java methods.
 * 
 * @author cmarilier
 */
public class WillFinallyNotBeDeliveredStepDefinitions extends NotificationControllerTest {

	private static final String ROUTE = "/orders/{id}/will-finally-not-be-delivered";

	private static String customerId = "0";

	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderRepository orderRepository;

	@Given("^I am \"([\\S]+)\" Alexandre \"([^\"]*)\"$")
	public void i_am(Customer.Gender customerGender, String customerName) {

		int id = Integer.parseInt(customerId);

		customer = new Customer(String.valueOf(++id), customerGender, customerName);
		//customerRepository.save(customer);
	}

	@When("^the drone assigned to deliver my order (\\d+) is no longer able to come to the address \"([^\"]*)\"$")
	public void the_drone_assigned_to_deliver_my_order_is_no_longer_able_to_come_to_the_address(int orderId, String deliveryAddress) {

		order = new Order(String.valueOf(orderId), customerId, deliveryAddress);
		//orderRepository.save(order);
	}

	@Then("^the Fleet Service notifies me that my order (\\d+) will finally not be delivered due to \"([^\"]*)\"$")
	public void the_fleet_service_notifies_me_that_my_order_will_finally_not_be_delivered_due_to(int orderId, String reason) {

		/*ResponseEntity<?> responseEntity = notifyThatOrderWillFinallyNotBeDelivered(ROUTE, String.valueOf(orderId), reason);
		Object responseBody = responseEntity.getBody();
		System.out.println(responseBody.toString());*/
	}

}
