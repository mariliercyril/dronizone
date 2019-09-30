package com.scp.dronizone.fleet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FleetService {

	public static void main(String[] args) {

		SpringApplication client = new SpringApplication(FleetService.class);

		Map<String, Object> map = new HashMap<>();
		map.put("server.port", 9004);
		client.setDefaultProperties(map);

		client.run(args);
	}

}
