package com.scp.dronizone.notification;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import com.scp.dronizone.notification.dao.entity.Customer;
import com.scp.dronizone.notification.dao.entity.CustomerOrder;
import com.scp.dronizone.notification.dao.entity.CustomerOrderRepository;
import com.scp.dronizone.notification.dao.entity.CustomerRepository;

/**
 * The {@code DatabaseLoader} class allows to initialize the data for the <b>notification</b> service.
 * 
 * @author cmarilier
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

	private final CustomerRepository customerRepository;
	private final CustomerOrderRepository customerOrderRepository;

	@Autowired
	public DatabaseLoader(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {

		this.customerRepository = customerRepository;
		this.customerOrderRepository = customerOrderRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		Customer customer = new Customer("Foo");
		customerRepository.save(customer);

		CustomerOrder customerOrder = new CustomerOrder("oof", customer);
		customerOrderRepository.save(customerOrder);
	}

}
