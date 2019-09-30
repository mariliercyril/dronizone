package com.scp.dronizone.order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderService {

	public static void main(String[] args) {

		SpringApplication client = new SpringApplication(OrderService.class);

		Map<String, Object> map = new HashMap<>();
		map.put("server.port", 9001);
		client.setDefaultProperties(map);

		client.run(args);

		System.out.println("Order Service started...");
	}

}
