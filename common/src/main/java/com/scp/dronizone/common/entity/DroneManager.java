package com.scp.dronizone.common.entity;

import com.scp.dronizone.common.states.DroneState;

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
    public static void registerNewDrone(Drone drone) throws Exception {
        if (drones.get(drone.getId()) == null)
            drones.put(drone.getId(), drone);
        else
            throw new Exception("A drone with the ID #" + drone.getId() + " already exists in the DB.");
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
