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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/connected")
    public String connected() {
        LOG.warn("GET Request on /order/connected");
        return "Connected !";
    }

    @RequestMapping("/rest")
    public void restTemplatetest() {
        LOG.warn("Test for templateRest ");

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9002/warehouse/items";
//        String testResponse = restTemplate.getForObject(url, String.class);
//        LOG.warn("REST response : " + testResponse);
        ParameterizedTypeReference<List<Item>> ptr = new ParameterizedTypeReference<List<Item>>() {
        };
        ResponseEntity<List<Item>> response = restTemplate.exchange(url, HttpMethod.GET, null, ptr);

        List<Item> itemList = response.getBody();
        LOG.warn("Item list size : " + itemList.size());
        LOG.warn("Item list : ");
        for (Item item : itemList) {
            LOG.warn(item.toString());
        }

    }

    @RequestMapping("/items")
    public List<Item> browseItem() {
        LOG.warn("GET Request on /order/items");

        List<Item> items = Warehouse.getItems();
        if (items != null) {
            return items;
        }
        return new ArrayList<Item>();
    }

    @RequestMapping("/create")
    public Order createOrder(@RequestParam(value = "id", required = true) String itemId) {
        LOG.warn("GET Request on /order/create with parameter : " + itemId);
        Order newOrder = Warehouse.createOrder(itemId);
        OrderManager.addOrder(newOrder);
        return newOrder;
    }

    @PostMapping("/")
    public Order createOrder(@RequestBody Order order){
        LOG.warn("GET Request on /order/create with parameter : " + order.getIdOrder());
        OrderManager.addOrder(order);
        return order;
    }

    @PutMapping("/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable Integer id){
        LOG.warn("GET Request on /order/create with parameter : " + order.getIdOrder());
        OrderManager.addOrder(order);
        return order;
    }

    @GetMapping("/")
    public List<Order> getOrders() {
        return OrderManager.getOrders();
    }
}
