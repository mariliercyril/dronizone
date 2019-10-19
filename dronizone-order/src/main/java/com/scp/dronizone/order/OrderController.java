package com.scp.dronizone.order;

import com.scp.dronizone.order.entity.Item;
import com.scp.dronizone.order.entity.Order;
import com.scp.dronizone.order.entity.OrderManager;
import com.scp.dronizone.order.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/connected")
    public String connected() {
        LOG.warn("GET Request on /orders/connected");
        return "Connected !";
    }

//    @RequestMapping("/rest")
//    public List<Item> restTemplatetest() {
//        LOG.warn("Test for templateRest ");
//
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:9002/warehouse/items";
//
//        ParameterizedTypeReference<List<Item>> ptr = new ParameterizedTypeReference<List<Item>>() {
//        };
//        ResponseEntity<List<Item>> response = restTemplate.exchange(url, HttpMethod.GET, null, ptr);
//
//        List<Item> itemList = response.getBody();
//        LOG.warn("Item list size : " + itemList.size());
//        LOG.warn("Item list : ");
//        for (Item item : itemList) {
//            LOG.warn(item.toString());
//        }
//        return itemList;
//    }

    @RequestMapping("/items")
    public List<Item> browseItem() {
        LOG.warn("Request on /orders/items");

//        List<Item> items = Warehouse.getItems();
//        if (items != null) {
//            return items;
//        }
//        return new ArrayList<Item>();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9002/warehouse/items";

        ParameterizedTypeReference<List<Item>> ptr = new ParameterizedTypeReference<List<Item>>() {
        };
        ResponseEntity<List<Item>> response = restTemplate.exchange(url, HttpMethod.GET, null, ptr);

        List<Item> itemList = response.getBody();
        LOG.warn("Item list size : " + itemList.size());
        LOG.warn("Item list : ");
        for (Item item : itemList) {
            LOG.warn(item.toString());
        }
        return itemList;
    }

    @PostMapping("/")
    public Order createOrder(@RequestBody Order order) {
        LOG.warn("POST Request on /orders/");
        LOG.warn("Passed object  : " + order.toString());
        OrderManager.addOrder(order);
        return order;
    }

    @PutMapping("/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable Integer id) {
        LOG.warn("PUT Request on /orders/{id} with parameter : " + id);
        OrderManager.addOrder(order);
        return order;
    }

    @GetMapping("/")
    public List<Order> getOrders() {
        LOG.warn("GET Request on /orders/");
        return OrderManager.getOrders();
    }

}
