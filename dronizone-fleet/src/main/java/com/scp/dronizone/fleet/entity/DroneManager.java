package com.scp.dronizone.fleet.entity;

import com.scp.dronizone.fleet.repository.DroneRepository;
import com.scp.dronizone.fleet.repository.DroneTelemetryRepository;
import com.scp.dronizone.fleet.states.DroneState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DroneManager {

    private DroneRepository droneRepository;
    private DroneTelemetryRepository telemetryRepository;

    @Autowired
    public DroneManager(DroneRepository droneRepository, DroneTelemetryRepository telemetryRepository) {
        this.droneRepository = droneRepository;
        this.telemetryRepository = telemetryRepository;
    }

    /**
     * Récupérer un Drone via son ID
     *
     * @param id ID du drone à récupérer
     *
     * @return le Drone recherché ou null s'il n'existe pas dans la BD
     */
    public Optional<Drone> getDroneById(Integer id) {
        return droneRepository.findDroneById(id);
    }

    /**
     * Récupérer l'intégralité de la BD
     *
     * @return {Collection<Drone>}
     *  L'ensemble de la DB sous forme de collection
     */
    public Collection<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    /**
     * Ajouter un Drone à la BD
     *
     * @param drone le Drone à ajouter à la BD
     *
     * @throws Exception
     */
    public Drone registerNewDrone(Drone drone) throws Exception {
        if (!droneRepository.findDroneById(drone.getId()).isPresent()) {
            droneRepository.insert(drone);
            telemetryRepository.insert(drone.getPosition());
            return drone;
        }
        else
            throw new Exception("A drone with the ID #" + drone.getId() + " already exists in the DB.");
    }

    /**
     * `.save` un Drone, ce qui permet de PUT/UPDATE un Drone dans la BD
     *
     * @param drone le Drone à sauvegarder/MàJ dans la BD
     *
     * @return le Drone ainsi sauvegardé/MàJ
     */
    public Drone putOrUpdateDrone(Drone drone) {
        // save ne remplace pas, c'est à nous de manuellement supprimer
        if (droneRepository.findDroneById(drone.getId()).isPresent())
            droneRepository.deleteByDroneId(drone.getId());

        telemetryRepository.save(drone.getPosition());
        return droneRepository.save(drone);
    }

    /**
     * Change l'état de batterie d'un Drone, s'il existe dans la BD
     *
     * Flemme de recopier ça deux fois donc j'en fait une fonction
     *  (pas d'imagination pour une route autre que POST... mais quelque chose comme "/set?attr=&value=" ou je sais pas...
     *
     * @param id ID du drone à récupérer
     *
     * @param newState nouvel état
     *
     * @return le Drone
     */
    public Drone updateDroneStateAttribute(int id, DroneState newState) throws Exception {
        Optional<Drone> drone = droneRepository.findDroneById(id);
        if (drone.isPresent()) {
            drone.get().setStatus(newState);
            return droneRepository.save(drone.get());
        } else
            throw new Exception("Drone #" + id + " not found");
    }
    /**
     * Passer un Drone en mode RECHARGING
     * todo pour l'US#4
     *  et potentiellement US#5 aussi
     *  Envisager de trigger une MàJ du niveau de batterie lors du changement de cet attr, etc...
     *
     *
     * @param id ID du drone à récupérer
     *
     * @return le Drone
     */
    public Drone deactivateDrone(int id) throws Exception {
        return updateDroneStateAttribute(id, DroneState.RECHARGING);
    }
    public Drone activateDrone(int id) throws Exception {
        return updateDroneStateAttribute(id, DroneState.AVAILABLE);
    }

    /**
     * Vider la HashMap servant de DB
     * pour les Tests
     */
    public void resetDrones() {
        droneRepository.deleteAll();
    }
    /**
     * Vider la HashMap servant de BD de télémétrie
     */
    public void resetDronesTelemetry() {
        telemetryRepository.deleteAll();
    }

    /**
     * Empties the DB then insert all given data
     *
     * @param newDrones iterable set of new Drones
     *
     * @return the new DB
     */
    public Collection<Drone> setAllDrones(Iterable<Drone> newDrones) {
        droneRepository.deleteAll();
        // droneRepository.insert(newDrones);
        for (Drone d : newDrones) {
            putOrUpdateDrone(d);
            // telemetryRepository.insert(d.getPosition());
        }

        return getAllDrones();
    }

    /**
     * Rappeler tous les Drones actuellement en cours de livraison pour cause urgente (US#5)
     *  pour US#5
     */
    public void recallAllActiveDrones() {
        Iterable<Drone> deliveringDrones = droneRepository.findAllDronesByDroneState(DroneState.DELIVERING);
        for (Drone drone : deliveringDrones) {
            // thread
            drone.forceRecall();    // ne fait rien
            // update DB
            droneRepository.save(drone);

            System.out.println("Drone #" + drone.getId() + " was recalled from its delivery and is now " + drone.getStatus());
        }
    }

    /**
     * Tente de trouver un Drone disponible pour une livraison
     *
     * @return un drone avec le status AVAILABLE
     *
     * @throws Exception si aucun Drone n'est actuellement disponible
     */
    public Drone getOneAvailableDrone() throws Exception {
        Iterable<Drone> availableDrones = droneRepository.findAllDronesByDroneState(DroneState.AVAILABLE);

        Drone drone;
        if (availableDrones.iterator().hasNext())
            drone = availableDrones.iterator().next();
        else
            throw new Exception("No available Drone was found. Try again later"); // todo file d'attente ou refaire la requête ou, au moins, informer le fail via un 500

        System.out.println("availableDrone: " + drone);
        System.out.println("availableDrone: " + drone);
        System.out.println("availableDrone: " + drone);
        System.out.println("availableDrone: " + drone);
        System.out.println("availableDrone: " + drone);
        System.out.println("availableDrone: " + drone);

        return drone;
    }

    /**
     * Affecte un Order
     *
     * @param order ID de l'Order à affecter au Drone
     *
     * @param droneId ID du Drone à qui l'on souhaite affecter l'Order
     *
     * @return le Drone à qui l'on a affecter l'Order
     */
    public Drone assignOrderIdToDroneById(Order order, int droneId, String fleetServiceUrl) throws Exception {
        Optional<Drone> drone = getDroneById(droneId);
        if (drone.isPresent()) {
            drone.get().setFLEET_SERVICE_URL(fleetServiceUrl);
            drone.get().assignOrder(order);
            // sauvegarder le passage en mode "DELIVERING"
            return droneRepository.save(drone.get());
        } else
            throw new Exception("Drone #" + droneId + " not found");
    }

    /**
     * Enregistrer une nouvelle Position
     *
     * @param position nouvelle position à enregistrer
     *
     * @return la position enregistrée
     */
    public DronePosition logNewDronePosition(DronePosition position) {
        return telemetryRepository.insert(position);
    }

    /**
     * Récupérer l'ensemble
     *
     * @param droneId ID du Drone dont on veut la télémétrie
     *
     * @return l'ensemble de la télémétrie du Drone
     */
    public DronePosition[] getDronePositions(Integer droneId) throws Exception {
        return telemetryRepository.findAllTelemetryByDroneId(droneId);
    }
}
