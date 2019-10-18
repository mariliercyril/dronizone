package com.scp.dronizone.order;

import com.scp.dronizone.order.entity.Item;
import com.scp.dronizone.order.entity.Order;
import com.scp.dronizone.order.entity.OrderManager;
import com.scp.dronizone.order.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping("/items")
    public List<Item> browseItem() {
        LOG.warn("GET Request on /order/items");
        List<Item> items = Warehouse.getItems();
        if(items != null) {
            return items;
        }
        return new ArrayList<Item>();
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
