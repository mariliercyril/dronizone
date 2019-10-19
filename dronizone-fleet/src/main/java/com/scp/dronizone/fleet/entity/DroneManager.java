package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.DroneState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * "Fausse" DB de Drones, en attendant mySQL
 *
 * tout en static parce que...
 */
public class DroneManager {
    public static AtomicInteger counter = new AtomicInteger();
    static HashMap<Integer, Drone> drones = new HashMap<>();

    public DroneManager() {
    }

    /**
     * Récupérer un Drone via son ID
     *
     * @param {Integer} id
     *  ID du drone à récupérer
     *
     * @return {Drone|null}
     *  le Drone recherché ou null s'il n'existe pas dans la "BD" (HashMap)
     */
    public static Drone getDroneById(Integer id) {
        return drones.get(id);
    }

    /**
     * Récupérer l'intégralité de la BD
     *
     * @return {Collection<Drone>}
     *  L'ensemble de la DB sous forme de collection
     */
    public static Collection<Drone> getAllDrones() {
        return drones.values();
    }

    /**
     * Ajouter un Drone à la BD
     *
     * @param {Drone} drone
     *  le Drone à ajouter à la BD
     * @throws Exception
     */
    public static Drone registerNewDrone(Drone drone) throws Exception {
        if (drones.get(drone.getId()) == null) {
            drones.put(drone.getId(), drone);
            return drone;
        }
        else
            throw new Exception("A drone with the ID #" + drone.getId() + " already exists in the DB.");
    }

    /**
     * Vider la HashMap servant de DB
     * pour les Tests
     */
    public static void resetDrones() {
        drones.clear();
    }

    public static Collection<Drone> setAllDrones(Iterable<Drone> newDrones) {

        System.out.println("type of newDrones --> " + newDrones.getClass().getSimpleName() );

        if (!(newDrones instanceof HashMap))
            for (Drone drone:newDrones) {
                System.out.println("\n");
                System.out.println(drone);
            }

        // On reçoit un ArrayList de Drone, on les stock
        if (newDrones instanceof ArrayList) {
            HashMap<Integer, Drone> newBD = new HashMap<>();
            for (Drone drone: newDrones) {
                newBD.put(drone.getId(), drone);
            }
            // todo BD et pas static
            drones = newBD;
        }

        return getAllDrones();
    }


    /**
     * Rappeler tous les Drones actuellement en cours de livraison pour cause urgente (US#5)
     *  pour US#5
     */
    public static void recallAllActiveDrones() {
        for (Map.Entry<Integer, Drone> entry : drones.entrySet()) {
            if (entry.getValue().getStatus() == DroneState.DELIVERING) {
                entry.getValue().forceRecall(); // une fonction à part car, pour l'US#6, le Drone doit notifier le Client
                System.out.println("Drone #" + entry.getKey() + " was recalled from its delivery and is now " + entry.getValue().getStatus());
            }
        }
    }
}
