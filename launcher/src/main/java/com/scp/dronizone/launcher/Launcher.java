package com.scp.dronizone.launcher;

import com.scp.dronizone.common.entity.Item;
import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import com.scp.dronizone.fleet.FleetService;

import com.scp.dronizone.notification.NotificationService;

import com.scp.dronizone.order.OrderService;

import com.scp.dronizone.warehouse.service.WarehouseService;

public class Launcher {

	public Launcher() {

		OrderService.main(new String[]{"9001"});
		WarehouseService.main(new String[]{"9002"});
		NotificationService.main(new String[]{"9003"});
		FleetService.main(new String[]{"9004"});
	}

	public static void main(String[] args) {

		System.out.println("Launcher starts services...");

		new Launcher();

		Item myItem = new Item("2");
		Order myOrder;
		Warehouse.addItem(myItem);
		myOrder = Warehouse.createOrder(myItem.getIdItem());
		OrderManager.addOrder(myOrder);


		System.out.println("Finish");

//		System.exit(0);
	}

}
