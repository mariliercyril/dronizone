package com.scp.dronizone.order;

import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/connected")
    public String connected() {
        return "Connected !";
    }

    @RequestMapping("/create")
    public Order createOrder(@RequestParam(value = "id", required = true) String itemId){
        Order newOrder = Warehouse.createOrder(itemId);
        OrderManager.addOrder(newOrder);
        return newOrder;
    }
}
