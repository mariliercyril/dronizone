package com.scp.dronizone.services;

import com.scp.dronizone.order.entity.Item;
import com.scp.dronizone.order.entity.Order;
import com.scp.dronizone.order.entity.OrderManager;
import com.scp.dronizone.order.entity.Warehouse;
import com.scp.dronizone.order.OrderController;
import org.junit.Ignore;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerServiceImplTest {
    OrderController customerService;
    Item item;
    RestTemplate restTemplate;
    String warehouseURL = "http://localhost:9002/warehouse/";
    String orderServiceURL = "http://localhost:9001/orders/";

    @org.junit.Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();

        OrderManager.resetOrders();
        customerService = new OrderController();

        item = new Item("125");
        HttpEntity<Item> request = new HttpEntity<Item>(item);
        restTemplate.delete(warehouseURL + "items");
        restTemplate.postForObject(warehouseURL + "items", request, Item.class);

    }

    @org.junit.Test
    public void browseItem() {
        List<Item> browseResult = customerService.browseItem();
        assertEquals(1, browseResult.size());
        assertEquals("125", browseResult.get(0).getIdItem());
    }

    @org.junit.Test
    public void orderItem() {
        Order order = new Order();
        order.addItem(customerService.browseItem().get(0));
        customerService.createOrder(order);

        ParameterizedTypeReference<List<Order>> ptr = new ParameterizedTypeReference<List<Order>>() {
        };
        ResponseEntity<List<Order>> response = restTemplate.exchange(orderServiceURL, HttpMethod.GET, null, ptr);

        List<Order> orders = response.getBody();

        assertEquals(1, orders.size());
        assertEquals("125", orders.get(0).getItems().get(0).getIdItem());
    }
}