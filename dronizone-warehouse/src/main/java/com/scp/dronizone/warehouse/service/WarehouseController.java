package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.entity.OrderManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping("/pack")
    public void packOrder(@RequestParam(value = "id", required = true) int idOrder){
        OrderManager.setOrderPacked(idOrder);
    }

}
