package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.common.entity.Item;
import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import com.scp.dronizone.common.states.ProcessingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@RunWith(JUnitPlatform.class)
class WarehouseControllerTest {
    WarehouseController warehouseService;
    Item item;
    Order order;

    @BeforeEach
    void setUp() {

        Warehouse.resetItemList();
        OrderManager.resetOrders();

        warehouseService = new WarehouseController();

        item = new Item("125");
        Warehouse.addItem(item);

        order = Warehouse.createOrder(item.getIdItem());
        OrderManager.addOrder(order);
    }

    @Test
    void getOrders() {
        List<Order> myOrders;

        myOrders = warehouseService.getOrders();

        assertEquals(myOrders.get(0),order);
    }

    @Test
    void packOrder() {
        warehouseService.packOrder(0);

        assertEquals(order.getProcessingState(),ProcessingState.PACKED);
    }
}