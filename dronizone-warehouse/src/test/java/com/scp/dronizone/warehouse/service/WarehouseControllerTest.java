package com.scp.dronizone.warehouse.service;

import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.entity.OrderManager;
import com.scp.dronizone.warehouse.states.ProcessingState;
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
    Order order;

    @BeforeEach
    void setUp() {

        OrderManager.resetOrders();

        warehouseService = new WarehouseController();


        order = new Order(1);
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
        warehouseService.packOrder(order.getId());

        assertEquals(order.getProcessingState(), ProcessingState.PACKED);
    }
}