package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.warehouse.entity.Item;
import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.entity.OrderManager;
import com.scp.dronizone.warehouse.entity.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private static final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    Warehouse warehouse;

    @Autowired
    OrderManager orderManager;

    @Value("${fleet.service.url}")
    private String FLEET_SERVICE_URL;

    @Value("${order.service.url}")
    private String ORDER_SERVICE_URL;

    public WarehouseController(){
    }

    @GetMapping("/connected")
    public HttpStatus connected() {
        return HttpStatus.OK;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderManager.getUnpackedOrders();
    }

    @PostMapping("/orders")
    public ResponseEntity.BodyBuilder newOrder(@RequestBody Order order) {
        orderManager.displayPendingOrders();
        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public Item addItem(@RequestParam String idItem) {
        Item item = warehouse.getItem(idItem);
        return item;
    }

    @PostMapping("/items")
    public ResponseEntity.BodyBuilder addItem(@RequestBody Item item) {
        warehouse.addItem(item);
        return ResponseEntity.status(HttpStatus.CREATED);
    }

    @DeleteMapping("/items")
    public void deleteItems() {
        LOG.warn("DELETE Request on warehouse/items");
        warehouse.resetItems();
    }

    @PutMapping("/orders/pack/{idOrder}")
    public String packOrder(@PathVariable int idOrder) {
        Order order = orderManager.setOrderPacked(idOrder);

        RestTemplate restTemplate_fleet = new RestTemplate();
        HttpEntity<Order> request_fleet = new HttpEntity<Order>(order);
        ResponseEntity<String> response_fleet = restTemplate_fleet.exchange("http://"+FLEET_SERVICE_URL+ "/fleet/assign", HttpMethod.POST, request_fleet, String.class);

        return "OK";
    }

    @GetMapping("/items")
    public List<Item> getItems() {
        return warehouse.getItems();
    }
}
