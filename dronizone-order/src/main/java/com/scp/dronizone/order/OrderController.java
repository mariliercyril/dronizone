package com.scp.dronizone.order;

import com.scp.dronizone.common.entity.Item;
import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
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

    @RequestMapping("/create")
    public Order createOrder(@RequestParam(value = "id", required = true) String itemId){
        LOG.warn("GET Request on /order/create with parameter : " + itemId);
        Order newOrder = Warehouse.createOrder(itemId);
        OrderManager.addOrder(newOrder);
        return newOrder;
    }
}
