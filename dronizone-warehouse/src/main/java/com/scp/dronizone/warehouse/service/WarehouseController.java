package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.entity.OrderManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @GetMapping("/connected")
    public String connected() {
        return "Connected !";
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return OrderManager.getUnpackedOrders();
    }

    @PostMapping("/orders")
    public List<Order> newOrder(@RequestBody Order order) {
        OrderManager.addOrder(order);
        return OrderManager.getOrders();
    }

    @PutMapping("/orders/pack/{idOrder}")
    public String packOrder(@PathVariable int idOrder){
        OrderManager.setOrderPacked(idOrder);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Integer> request = new HttpEntity<Integer>(idOrder);
        ResponseEntity<String> response = restTemplate.exchange("http://fleet:" + 9004 + "/fleet/assign", HttpMethod.POST, request, String.class);

        return "OK";
    }

}
