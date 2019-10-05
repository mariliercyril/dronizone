package com.scp.dronizone.launcher;

import com.scp.dronizone.common.entity.*;
import com.scp.dronizone.common.states.DroneBatteryState;
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

		// US#2
		Item myItem = new Item("2");
		Order myOrder;
		Warehouse.addItem(myItem);
		myOrder = Warehouse.createOrder(myItem.getIdItem());
		OrderManager.addOrder(myOrder);


		// User Story #4
		/**
		 * On crée 2 Drones dans la "BD' (la HashMap dans DroneManager)
		 * l'un a une batterie LOW, et on peut donc ordonner au Drone de se recharger via une requête REST "/{id}/deactivate"
		 * todo faire ça dans un test
		 * 	MàJ les états/niveaux de batteries (sinon, on dit au Drone de se recharger mais rien ne se passe... chouette)
		 */
		System.out.println(" ========= US#4 ========= ");
		Drone fullBatteryDrone = new Drone();
		System.out.println(fullBatteryDrone.toString());
		System.out.println("Drone #" + fullBatteryDrone.getId() + ", with battery " + fullBatteryDrone.getBatteryState() + ", is being added to the 'DB'.");
		try {
			DroneManager.registerNewDrone(fullBatteryDrone);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Drone lowBatteryDrone = new Drone();
		lowBatteryDrone.setBatteryState(DroneBatteryState.LOW);
		System.out.println(lowBatteryDrone.toString());
		System.out.println("Drone #" + lowBatteryDrone.getId() + ", with battery " + lowBatteryDrone.getBatteryState() + ", is being added to the 'DB'.");
		try {
			DroneManager.registerNewDrone(lowBatteryDrone);
		} catch (Exception e) {
			e.printStackTrace();
		}


		System.out.println("Finish");

//		System.exit(0);
	}

}
