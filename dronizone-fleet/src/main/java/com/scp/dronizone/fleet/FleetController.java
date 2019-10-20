package com.scp.dronizone.fleet;

import com.scp.dronizone.fleet.entity.Drone;
import com.scp.dronizone.fleet.entity.DroneManager;
import com.scp.dronizone.fleet.states.DroneBatteryState;
import com.scp.dronizone.fleet.states.DroneState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/fleet")
public class FleetController {
    private static final Logger LOG = LoggerFactory.getLogger(FleetController.class);


    /**
     * Prouve que le Service est en ligne
     *
     * @return {String}
     *  OK string
     */
    @RequestMapping({"", "/", "/connected"})
    public String home(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        return "Connected !";
    }

    /**
     * Récupérer un Drone via son ID
     * todo plutôt pour tester/debug, pour l'US#4 on ne veut QUE le niveau de batterie,
     *  mais je me dis que si un drone EN COURS DE LIVRAISON est en batterie LOW... on veut pas lui faire faire demi-tour
     *
     * @param {int} id
     *  ID du Drone à trouver dans la BD
     *
     * @return {JSON/Drone}
     *  Le Drone, s'il existe, au format JSON
     */
    @GetMapping(path = "/drones/{id}")
    public @ResponseBody
    Drone getDroneById(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        LOG.info("Trying to retrieve the Drone with ID: " + id + " from the DB...");
        Drone zeDrone = DroneManager.getDroneById(id);
        if (zeDrone != null)
            return zeDrone;
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone #" + id + " not found");

    }

    /**
     * Récupérer le niveau de Battery d'un Drone via son ID
     *
     * @param {int} id
     *  ID du drone à récupérer
     *
     * @return {DroneBatteryState|null}
     *  le niveau de Battery du Drone recherché ou null s'il n'existe pas dans la "BD" (HashMap)
     */
    @GetMapping(path = "/drones/{id}/battery")
    public @ResponseBody
    DroneBatteryState getDroneBatteryById(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        LOG.info("Trying to retrieve the Drone with ID: " + id + "'s battery status from the DB...");
        Drone zeDrone = DroneManager.getDroneById(id);
        if (zeDrone != null)
            return zeDrone.getBatteryState();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone #" + id + " not found");
    }


    /**
     * Récupérer l'intégralité de la BD
     *
     * @return {Collection<Drone>}
     *  L'ensemble de la DB sous forme de collection, JSON Array par défaut
     */
    @GetMapping(path = "/drones")
    public @ResponseBody Iterable<Drone> getAllDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        return DroneManager.getAllDrones();
    }

    /**
     * Passer un Drone en mode RECHARGING
     * todo pour l'US#4
     *  et potentiellement US#5 aussi
     *  Envisager de trigger une MàJ du niveau de batterie lors du changement de cet attr, etc...
     *
     *
     * @param {int} id
     *  ID du drone à récupérer
     *
     * @return {Drone} le Drone
     */
    @GetMapping(path = "/drones/{id}/deactivate")
    public @ResponseBody
    Drone deactivateDrone(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        return updateDroneStateAttribute(id, DroneState.RECHARGING);
    }
    @GetMapping(path = "/drones/{id}/activate")
    public @ResponseBody
    Drone activateDrone(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        return updateDroneStateAttribute(id, DroneState.AVAILABLE);
    }
    /**
     * Change l'état de batterie d'un Drone, s'il existe dans la BD
     *
     * Flemme de recopier ça deux fois donc j'en fait une fonction
     *  (pas d'imagination pour une route autre que POST... mais quelque chose comme "/set?attr=&value=" ou je sais pas...
     *
     * @param {int} id
     *  ID du drone à récupérer
     *
     * @param {DroneState} newState
     *  nouvel état
     *
     * @return {Drone} le Drone
     */
    public Drone updateDroneStateAttribute(int id, DroneState newState) {
        Drone zeDrone = DroneManager.getDroneById(id);
        if (zeDrone != null) {
            zeDrone.setStatus(newState);
            return zeDrone;
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone #" + id + " not found");
    }

    @GetMapping(path = "/drones/totalrecall")
    public @ResponseBody String emergencyRecallAllDeliveringDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        DroneManager.recallAllActiveDrones();
        return "Done."; // 200
    }

    /**
     * Ajouter un nouveau Drone à la HashMap
     *
     * @param {Drone} drone
     *  le Drone a ajouter
     *
     * @param {HttpServletRequest} request
     *
     * @return {Drone}
     *  le Drone ajouté
     */
    @PostMapping("/drones")
    public @ResponseBody
    Drone addNewDrone(@RequestBody Drone drone, HttpServletRequest request) throws Exception {
        LOG.info("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        LOG.info("Trying to add\n" + drone.toString());

        try {
            return DroneManager.registerNewDrone(drone);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    /**
     * Vider la HashMap
     *  utile pour les tests
     *
     * @return
     *  la HashMap (sera donc un Array vide, servant de "preuve")
     */
    @RequestMapping ("/drones/reset")
    public @ResponseBody Iterable<Drone> removeAllDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + "?" + request.getQueryString());
        DroneManager.resetDrones();
        return DroneManager.getAllDrones();
    }

    // TODO ajouter une route pour PUT un Array de Drone --> remplacer la base
    @RequestMapping(path = "/drones", method = RequestMethod.PUT)
    public @ResponseBody Iterable<Drone> setWholeDroneDatabase(@RequestBody Iterable<Drone> drones, HttpServletRequest request) {
        LOG.warn("\nRequest on " + request.getRequestURI() + "?" + request.getQueryString());
        LOG.warn("\nRequestBody:");
        for (Drone drone: drones) {
            LOG.info("\n" + drone.toString());
        }

        LOG.warn("\n\nString.valueOf(drones)");
        LOG.warn(String.valueOf(drones));

        // Il faut impérativement passer le bon type, aka HashMap<int, Drone>
        return DroneManager.setAllDrones(drones);
    }

    /**
     * Echo/Marco-Polo
     * Renvoie l'Objet reçu
     * pour debug
     * @// TODO: 18/10/2019 supprimer
     * @param received Le message reçu
     * @return {Object}
     */
    @RequestMapping(path = "/marco")
    public @ResponseBody Object polo(@RequestBody Object received) {
        return received;
    }

    @PostMapping("/assign")
    public String assignNewOrder(Integer idOrder){
        String message = "OK";
        return message;
    }
}
