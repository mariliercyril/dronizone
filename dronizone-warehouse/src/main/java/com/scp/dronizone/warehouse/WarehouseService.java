package com.scp.dronizone.warehouse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseService {

	public static void main(String[] args) {

		SpringApplication client = new SpringApplication(WarehouseService.class);

		Map<String, Object> map = new HashMap<>();
		map.put("server.port", 9002);
		client.setDefaultProperties(map);

		client.run(args);

		System.out.println("Warehouse Service started...");
	}

}
