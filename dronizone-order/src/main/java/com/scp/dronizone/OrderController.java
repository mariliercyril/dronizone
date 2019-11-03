package com.scp.dronizone;

import com.scp.dronizone.order.entity.Item;
import com.scp.dronizone.order.entity.Order;
import com.scp.dronizone.order.entity.OrderManager;

import com.scp.dronizone.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderManager orderManager;

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Value("${warehouse.service.url}")
    private String WAREHOUSE_SERVICE_URL;

    @GetMapping("/connected")
    public String connected() {
        LOG.warn("GET Request on /orders/connected");
        return "Connected !";
    }

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderManager orderManager) {
        this.orderRepository = orderRepository;

        this.orderManager = orderManager;
    }

    @RequestMapping("/items")
    public List<Item> browseItem() {
        LOG.warn("Request on /orders/items");

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + WAREHOUSE_SERVICE_URL + "/warehouse/items";

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

    @PostMapping("/new")
    public Order createOrder(@RequestBody Order order) {
        LOG.warn("POST Request on /orders/");
        LOG.warn("Passed object  : " + order.toString());
//        this.orderManager.addOrder(order);

        orderRepository.save(order);

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Order> request = new HttpEntity<Order>(order);
            ResponseEntity<String> response = restTemplate.exchange("http://" + WAREHOUSE_SERVICE_URL + "/warehouse/orders", HttpMethod.POST, request, String.class);
        }catch (Exception e){
            LOG.warn("ERROR  : " + e.toString());
        }

        return order;
    }

    @PostMapping("/")
    public Order createNewOrder(@RequestBody List<Item> items) {
        Order order = this.orderManager.createOrder(items);
        this.orderManager.addOrder(order);

        return order;
    }


    @PutMapping("/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable Integer id) {
        LOG.warn("PUT Request on /orders/{id} with parameter : " + id);
        Order updateOrder = this.orderManager.updateOrder(order);
        return updateOrder;
    }

    @GetMapping("/")
    public List<Order> getOrders() {
        LOG.warn("GET Request on /orders/");
        return this.orderManager.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrders(@PathVariable Integer id) {
        LOG.warn("GET Request on /orders/");
        return this.orderManager.getOrderById(id);
    }

    @GetMapping("/addToBd/{id}")
    public void addToBd(@PathVariable Integer id) {
        Object thing = orderRepository.save(new Order(id, 1));
        LOG.warn(thing.toString());
    }

    @GetMapping("/getAllBd")
    public void getAllBd() {
        List<Order> thing = orderRepository.findAll();
        LOG.warn(thing.toString());
    }

    @DeleteMapping("/")
    public void deleteOrders() {
        orderManager.resetOrders();
    }

}
