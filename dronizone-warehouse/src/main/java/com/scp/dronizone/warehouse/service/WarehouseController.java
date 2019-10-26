package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.warehouse.entity.Item;
import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.entity.OrderManager;
import com.scp.dronizone.warehouse.entity.Warehouse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private static final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);

    @Value("${fleet.service.url}")
    private String FLEET_SERVICE_URL;

    @Value("${order.service.url}")
    private String ORDER_SERVICE_URL;

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

    @GetMapping("/addItem")
    public String addItem(@RequestParam(value = "itemId", required = true) String itemId) {
        LOG.warn("Adding item with id : " + itemId);
        Warehouse.addItem(new Item(itemId));
        LOG.warn("Item list now : " + Warehouse.getItems().size());
        return "Item Added (I think)";
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        LOG.warn("Adding item with id : " + item.getIdItem());
        Warehouse.addItem(item);
        return item;
    }

    @DeleteMapping("/items")
    public void deleteItems() {
        LOG.warn("DELETE Request on warehouse/items");
        Warehouse.resetItemList();
        LOG.warn("Item list now : " + Warehouse.getItems().size());
    }

    @PutMapping("/orders/pack/{idOrder}")
    public String packOrder(@PathVariable int idOrder) {
        Order order = OrderManager.setOrderPacked(idOrder);

        RestTemplate restTemplate_order = new RestTemplate();
        HttpEntity<Order> request_order = new HttpEntity<Order>(order);
        ResponseEntity<String> response_order = restTemplate_order.exchange("http://"+ORDER_SERVICE_URL+ "/orders/"+order.getIdOrder(), HttpMethod.PUT, request_order, String.class);

        RestTemplate restTemplate_fleet = new RestTemplate();
        HttpEntity<Order> request_fleet = new HttpEntity<Order>(order);
        ResponseEntity<String> response_fleet = restTemplate_fleet.exchange("http://"+FLEET_SERVICE_URL+ "/fleet/assign", HttpMethod.POST, request_fleet, String.class);

        return "OK";
    }

    @RequestMapping("/items")
    public List<Item> getAvailableItems() {
        return Warehouse.getItems();
    }
}
