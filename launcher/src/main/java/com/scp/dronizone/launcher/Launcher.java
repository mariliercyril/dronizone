package com.scp.dronizone.launcher;

import com.scp.dronizone.fleet.FleetService;

import com.scp.dronizone.notification.NotificationService;

import com.scp.dronizone.order.OrderService;

import com.scp.dronizone.warehouse.WarehouseService;

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

		System.exit(0);
	}

}
