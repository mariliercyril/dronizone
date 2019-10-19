package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.common.entity.Item;
import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private static final Logger LOG = LoggerFactory.getLogger(WarehouseController.class);

    @GetMapping("/connected")
    public String connected() {
        return "Connected !";
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return OrderManager.getUnpackedOrders();
    }

    @GetMapping("/addItem")
    public void addItem(@RequestParam(value = "itemId", required = true) String itemId) {
        LOG.warn("Adding item with id : " + itemId);
        Warehouse.addItem(new Item(itemId));
        LOG.warn("Item list now : " + Warehouse.getItems().size());
    }


    @RequestMapping("/pack")
    public void packOrder(@RequestParam(value = "id", required = true) String idOrder){
        OrderManager.setOrderPacked(idOrder);
    }

    @RequestMapping("/items")
    public List<Item> getAvailableItems(){
        return Warehouse.getItems();
    }

}
