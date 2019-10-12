package com.scp.dronizone.notification.controller;

import com.scp.dronizone.notification.dao.entity.Customer;
import com.scp.dronizone.notification.dao.entity.CustomerOrder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * The {@code AbandonmentNotificationStepDefinitions} class defines the Gherkin steps (in
 * the scenarios of the Cucumber <i>feature</i> "Does the drone assigned to our delivery
 * notify us when it is no longer able to fulfill its mission?" [USER STORY 6]) as Java methods.
 * 
 * @author cmarilier
 */
public class AbandonmentNotificationStepDefinitions extends NotificationControllerTest {

	private static final String ROUTE = "/abandonment";

	private static final String NOTIFICATION_FORMAT = "Your order %d will no longer be delivered.";

	@Given("I am a customer")
	public void i_am_a_customer() {}

	@And("^my name is \"([^\"]*)\"$")
	public void my_name_is(String customerName) {

		customer = new Customer(customerName);

		//customerRepository.save(customer);
	}

	@When("the drone assigned to deliver my order \"([^\"]*)\" is no longer able to fulfill its mission")
	public void the_drone_assigned_to_deliver_my_order_is_no_longer_able_to_fulfill_its_mission(String idOrder) {

		customerOrder = new CustomerOrder(idOrder, customer);

		//customerOrderRepository.save(customerOrder);
	}

	@Then("it notifies me that my order \"([^\"]*)\" will no longer be delivered")
	public void it_notifies_me_that_my_order_will_no_longer_be_delivered(int idOrder) {

		String notification = String.format(NOTIFICATION_FORMAT, idOrder);

		consumeNotificationService(ROUTE, notification);
	}

}
