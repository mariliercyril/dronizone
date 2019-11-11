package com.scp.dronizone.warehouse;


import com.scp.dronizone.warehouse.entity.Item;
import com.scp.dronizone.warehouse.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WarehouseService {

	public static void main(String[] args) {

        SpringApplication.run(WarehouseService.class, args);
	}

}
