package com.scp.dronizone.notification.controller.test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scp.dronizone.notification.model.entity.Customer;

import com.scp.dronizone.notification.model.repository.CustomerRepository;

/**	
 * The {@code CustomerControllerTest} class allows to post a customer for testing...
 * 
 * @author cmarilier
 */
@RestController
public class CustomerControllerTest {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerControllerTest(CustomerRepository customerRepository) {

		this.customerRepository = customerRepository;
	}

	@PostMapping(value = "/customers")
	public void insertCustomer(@RequestBody Customer customer) {

		customerRepository.save(customer);
	}

}
