package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.states.DroneState;
import com.scp.dronizone.fleet.states.ProcessingState;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * "Fausse" DB de Drones, en attendant mySQL
 *
 * tout en static parce que...
 */
public class DroneManager {
    public static AtomicInteger counter = new AtomicInteger();
    static HashMap<Integer, Drone> drones = new HashMap<>();
    static HashMap<Integer, List<DronePosition>> dronesTelemetry = new HashMap<>();

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
            dronesTelemetry.put(
                    drone.getId(),
                    new LinkedList(Arrays.asList(drone.getPosition()))
            );
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
    /**
     * Vider la HashMap servant de BD de télémétrie
     */
    public static void resetDronesTelemetry() {
        dronesTelemetry.clear();
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
            if (DroneState.DELIVERING == entry.getValue().getStatus()) {
                entry.getValue().forceRecall(); // une fonction à part car, pour l'US#6, le Drone doit notifier le Client
                System.out.println("Drone #" + entry.getKey() + " was recalled from its delivery and is now " + entry.getValue().getStatus());
            }
        }
    }

    /**
     * Tente de trouver un Drone disponible pour une livraison
     * @return un drone avec le status AVAILABLE
     * @throws Exception si aucun Drone n'est actuellement disponible
     */
    public static Drone getOneAvailableDrone() throws Exception {
        Drone availableDrone = null;
        for (Map.Entry<Integer, Drone> droneEntry: drones.entrySet()) {
            if (droneEntry.getValue().getStatus() == DroneState.AVAILABLE)  // inclure ceux RECHARGING ?
                return availableDrone = droneEntry.getValue();
        }
        throw new Exception("No available Drone was found. Try again later"); // todo file d'attente ou refaire la requête ou, au moins, informer le fail via un 500
    }
    /**
     * Affecte un Order
     * @param order ID de l'Order à affecter au Drone
     * @param droneId ID du Drone à qui l'on souhaite affecter l'Order
     * @return le Drone à qui l'on a affecter l'Order
     */
    public static Drone assignOrderIdToDroneById(Order order, int droneId, String fleetServiceUrl) {
        Drone drone = getDroneById(droneId);
        drone.setFLEET_SERVICE_URL(fleetServiceUrl);
        drone.assignOrder(order);
        return drone;
    }


    /**
     * Enregistrer une nouvelle Position
     * @param position nouvelle position à enregistrer
     * @return la position enregistrée
     */
    public static DronePosition logNewDronePosition(Integer droneId, DronePosition position) {
        // Pas de vérification ? On suppose qu'on peut LOG des positions sans
        if (dronesTelemetry.containsKey(droneId))
            dronesTelemetry.get(droneId).add(position);
        else
            dronesTelemetry.put(droneId, new LinkedList(Arrays.asList(position)));
        return position;
    }

    /**
     * Récupérer l'ensemble
     * @param droneId ID du Drone dont on veut la télémétrie
     * @return l'ensemble de la télémétrie du Drone
     */
    // todo Faut-il ne récupérer QUE jusqu'à 5 ans max ? (si oui, loop en partant de la fin et check le timestamp de la position)
    public static DronePosition[] getDronePositions(Integer droneId) throws Exception {
        if (!dronesTelemetry.containsKey(droneId))
            throw new Exception("No entry for this Drone ID (" + droneId + ")");
        List records = dronesTelemetry.get(droneId);
        return (DronePosition[]) records.toArray(new DronePosition[records.size()]);
    }
}
