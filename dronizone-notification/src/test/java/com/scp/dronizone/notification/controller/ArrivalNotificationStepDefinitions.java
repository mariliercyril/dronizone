package com.scp.dronizone.notification.controller;

import com.scp.dronizone.notification.dao.entity.Customer;
import com.scp.dronizone.notification.dao.entity.CustomerOrder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * The {@code ArrivalNotificationStepDefinitions} class defines the Gherkin steps
 * (in the scenarios of the Cucumber <i>feature</i> "Does the drone delivering our
 * order notify us that it will arrive shortly?" [USER STORY 3]) as Java methods.
 * 
 * @author cmarilier
 */
public class ArrivalNotificationStepDefinitions extends NotificationControllerTest {

	private static final String ROUTE = "/arrival";

	private static final String NOTIFICATION_FORMAT = "Your order %d vill be delivered at %s.";

	@Given("I am a lazy customer")
	public void i_am_a_lazy_customer() {}

	@And("^my name is ([^\"]*) and my address is ([^\"]*)$")
	public void my_name_is(String customerName, String customerAddress) {

		customer = new Customer(customerName);
		customer.setAddress(customerAddress);

		//customerRepository.save(customer);
	}

	@When("the drone delivering my order (\\d+) will arrive shortly")
	public void the_drone_delivering_my_order_will_arrive_shortly(Integer idOrder) {

		customerOrder = new CustomerOrder(String.valueOf(idOrder), customer);

		//customerOrderRepository.save(customerOrder);
	}

	@Then("it notifies me that my order (\\d+) vill be delivered at my address")
	public void it_notifies_me_that_my_order_vill_be_delivered_at_my_address(Integer idOrder) {

		//String customerAddress = ((customerOrderRepository.getOne(String.valueOf(idOrder))).getCustomer()).getAddress();
		String customerAddress = customer.getAddress();
		String notification = String.format(NOTIFICATION_FORMAT, idOrder, customerAddress);

		consumeNotificationService(ROUTE, notification);
	}

}
