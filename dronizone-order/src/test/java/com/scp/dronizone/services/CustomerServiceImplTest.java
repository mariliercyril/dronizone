package com.scp.dronizone.services;

import com.scp.dronizone.order.entity.Item;
import com.scp.dronizone.order.entity.Order;
import com.scp.dronizone.order.entity.OrderManager;
import com.scp.dronizone.order.entity.Warehouse;
import com.scp.dronizone.order.OrderController;

import static org.junit.Assert.*;

public class CustomerServiceImplTest {
    OrderController customerService;
    Item item;

    @org.junit.Before
    public void setUp() throws Exception {
        Warehouse.resetItemList();
        OrderManager.resetOrders();
        customerService = new OrderController();
        item = new Item("125");
        Warehouse.addItem(item);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        Warehouse.resetItemList();
        OrderManager.resetOrders();

    }

    @org.junit.Test
    public void browseItem() {

        assertEquals(1, customerService.browseItem().size());

    }

    @org.junit.Test
    public void orderItem() {
        Order order = new Order();
        order.addItem(new Item("125"));
        customerService.createOrder(order);
        assertEquals(1, OrderManager.getOrders().size());
    }
}