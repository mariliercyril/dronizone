package com.scp.dronizone.fleet;

import com.scp.dronizone.fleet.entity.*;
import com.scp.dronizone.fleet.states.DroneBatteryState;
import com.scp.dronizone.fleet.states.DroneState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping(path = "/fleet")
public class FleetController {

    private static final Logger LOG = LoggerFactory.getLogger(FleetController.class);

    @Value("${fleet.service.url}")
    String FLEET_SERVICE_URL;

    @Value("${notification.service.url}")
    String NOTIFICATION_SERVICE_URL;

    @Value("${order.service.url}")
    String ORDER_SERVICE_URL;

    @Autowired
    DroneManager droneManager;

    @Autowired
    public FleetController(DroneManager droneManager) {
        this.droneManager = droneManager;
    }

    /**
     * Prouve que le Service est en ligne
     *
     * @return {String}
     *  OK string
     */
    @RequestMapping({"", "/", "/connected"})
    public String home(HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));

        return "Connected !";
    }

    /**
     * Récupérer un Drone via son ID
     * todo plutôt pour tester/debug, pour l'US#4 on ne veut QUE le niveau de batterie,
     *  mais je me dis que si un drone EN COURS DE LIVRAISON est en batterie LOW... on veut pas lui faire faire demi-tour
     *
     * @param id ID du Drone à trouver dans la BD
     *
     * @return {JSON/Drone}
     *  Le Drone, s'il existe, au format JSON
     */
    @GetMapping(path = "/drones/{id}")
    public @ResponseBody Drone getDroneById(@PathVariable("id") int id, HttpServletRequest request) {
        if (request != null) {
            LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
            LOG.info("Trying to retrieve the Drone with ID: " + id + " from the DB...");
        }

        Optional<Drone> drone = droneManager.getDroneById(id);
        if (drone.isPresent())
            return drone.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone #" + id + " not found");

    }

    /**
     * Récupérer l'intégralité de la BD
     *
     * @return L'ensemble de la DB sous forme de collection, JSON Array par défaut
     */
    @GetMapping(path = "/drones")
    public @ResponseBody
    Iterable<Drone> getAllDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));

        return droneManager.getAllDrones();
    }

    /**
     * Ajouter un nouveau Drone
     *
     * @param drone le Drone a ajouter
     *
     * @return le Drone ajouté
     *
     * @throws Exception si un Drone avec cet ID existe déjà (drone_id, pas mongo_id)
     */
    @PostMapping(path = "/drones")
    public @ResponseBody
    Drone addNewDrone(@RequestBody Drone drone, HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        LOG.info("Trying to add\n" + drone.toString());

        try {
            drone.setFLEET_SERVICE_URL(FLEET_SERVICE_URL);
            return droneManager.registerNewDrone(drone);
        } catch (Exception e) {
            LOG.error(e.toString());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PutMapping("/drones")
    public @ResponseBody Iterable<Drone> setWholeDroneDatabase(@RequestBody Iterable<Drone> drones, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        LOG.warn("\nRequestBody:");
        for (Drone drone: drones) {
            LOG.info("\n" + drone.toString());
        }

        LOG.warn("\n\nString.valueOf(drones)");
        LOG.warn(String.valueOf(drones));

        return droneManager.setAllDrones(drones);
    }

    /**
     * MàJ un Drone
     *
     * @param droneId ID du Drone à MàJ (obligé, car il existe
     *
     * @param drone le Drone et ses nouvelles valeurs
     *
     * @return le Drone après MàJ
     */
    @PutMapping("/drones/{id}")
    public @ResponseBody
    Drone putOrUpdateDrone(@PathVariable("id") int droneId, @RequestBody Drone drone, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        LOG.info("Trying to PUT/UPDATE\n" + drone.toString());

        return droneManager.putOrUpdateDrone(drone);
    }


    /**
     * Récupérer le niveau de Battery d'un Drone via son ID
     *
     * @param id ID du drone à récupérer
     *
     * @return {DroneBatteryState|null}
     *  le niveau de Battery du Drone recherché ou null s'il n'existe pas dans la "BD" (HashMap)
     */
    @GetMapping(path = "/drones/{id}/battery")
    public @ResponseBody
    DroneBatteryState getDroneBatteryById(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        LOG.info("Trying to retrieve the Drone with ID: " + id + "'s battery status from the DB...");

        Drone drone = getDroneById(id, null);
        return drone.getBatteryState();
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
    @PutMapping(path = "/drones/{id}/deactivate")
    public @ResponseBody
    Drone deactivateDrone(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));

        return droneManager.deactivateDrone(id);
    }
    @PutMapping(path = "/drones/{id}/activate")
    public @ResponseBody
    Drone activateDrone(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));

        return droneManager.activateDrone(id);
    }

    /**
     * Force tous les Drones en cours de livraison (DELIVERING) à faire demi-tour
     *
     * @return "OK"
     */
    @GetMapping(path = {"/drones/totalrecall", "/drones/recall"})
    public @ResponseBody String emergencyRecallAllDeliveringDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        droneManager.recallAllActiveDrones();
        return "Done."; // 200
    }
    /**
     * Indiquer aux Drones FORCED_RETURNNG de recommencer leurs livraisons
     *
     * @return "OK"
     */
    @GetMapping(path = {"/drones/totalresume", "/drones/resume"})
    public @ResponseBody String resumeAllDeliveriesAfterAnEmergencyRecall(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        droneManager.resumeAllForcedReturningDrones();
        return "Done."; // 200
    }


    /**
     * Vider la BD
     *  utile pour les tests
     *
     * @return la BD (sera donc un Array vide, servant de "preuve")
     */
    @RequestMapping ("/drones/reset")
    public @ResponseBody Iterable<Drone> removeAllDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        droneManager.resetDrones();
        droneManager.resetDronesTelemetry();
        return droneManager.getAllDrones();
    }


    /**
     * Assigner un Drone à un colis
     * Parce qu'on suppose qu'il y a toujours des Drones de disponible, si ça n'est pas le cas
     * @param order ID de l'Order à livrer
     * @return le Drone affecté à la livraison,
     * ou une erreur si aucun n'est disponible (osef de la file d'attente. byebye le colis)
     * todo le service doit soit avoir une file d'attente, soit demander régulièrement à la BD si des livraisons sont à faire
     *  /drones/assign plutôt non ???
     */
    @PostMapping("/assign")
    public @ResponseBody Drone assignNewOrder(@RequestBody Order order, HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())) );

        // Trouver un Drone disponible pour une livraison (ne doit pas être en train de se recharger !RECHARGING et ne doit pas déjà être en cours de livraison) ?
        Drone drone = droneManager.getOneAvailableDrone();
        drone = droneManager.assignOrderIdToDroneById(order, drone.getId(), FLEET_SERVICE_URL);

        return drone;
    }

    // todo ajouter une route PUT juste pour init les tests ?
    @PostMapping("/drones/{id}/positions")
    public @ResponseBody DronePosition logNewDronePosition(@PathVariable("id") int droneId, @RequestBody DronePosition newPosition, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())) );
        return droneManager.logNewDronePosition(newPosition);
        // return droneManager.logNewDronePosition(droneId, newPosition);   // old
    }

    /**
     * Récupérer la position
     * @param droneId
     * @return
     * @throws Exception
     */
    @GetMapping("/drones/{id}/positions")
    public @ResponseBody DronePosition[] getDronePositions(@PathVariable("id") int droneId, HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())) );
        return droneManager.getDronePositions(droneId);
    }

    /**
     * Demander à FleetService d'envoyer une Notification à NotificationService
     * @param notification Notification à envoyer. Contient les attributs Type (soon/recall) et Order (ID)
     * @return OK au Drone pour signaler la bonne réception de la requête
     */
    @PostMapping("/notifications")
    public String notifyCustomer(@RequestBody Notification notification, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));

        // Aucune vérification particulière ?
        new RestTemplate().postForEntity("http://" + NOTIFICATION_SERVICE_URL + "/notifications", notification, String.class).getBody();

        // OK message (on n'informe pas le Drone si la notification n'est pas proprement retransmise.
        // Ce qui compte c'est de dire "j'ai bien reçu ton message" au Drone)
        return "Your notification was received and is being transmitted...";
    }
}
