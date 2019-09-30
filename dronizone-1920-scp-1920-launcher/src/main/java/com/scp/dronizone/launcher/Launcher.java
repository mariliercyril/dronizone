package com.scp.dronizone.launcher;

import com.scp.dronizone.fleet.FleetService;

import com.scp.dronizone.notification.NotificationService;

import com.scp.dronizone.order.OrderService;

import com.scp.dronizone.warehouse.WarehouseService;

public class Launcher {

	public static void main(String[] args) {

		System.out.println("Launcher starts services...");

		OrderService.main(args);
		WarehouseService.main(args);
		NotificationService.main(args);
		FleetService.main(args);
	}

}
