package com.scp.dronizone.fleet;

import com.scp.dronizone.fleet.entity.*;
import com.scp.dronizone.fleet.states.DroneBatteryState;
import com.scp.dronizone.fleet.states.DroneState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/fleet")
public class FleetController {
    private static final Logger LOG = LoggerFactory.getLogger(FleetController.class);

    @Value("${fleet.service.url}")
    String FLEET_SERVICE_URL;

    @Value("${notification.service.url}")
    String NOTIFICATION_SERVICE_URL;


    /**
     * Prouve que le Service est en ligne
     *
     * @return {String}
     *  OK string
     */
    @RequestMapping({"", "/", "/connected"})
    public String home(HttpServletRequest request) {
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
    public @ResponseBody
    Drone getDroneById(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
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
        Drone zeDrone = DroneManager.getDroneById(id);
        if (zeDrone != null)
            return zeDrone.getBatteryState();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone #" + id + " not found");
    }


    /**
     * Récupérer l'intégralité de la BD
     *
     * @return L'ensemble de la DB sous forme de collection, JSON Array par défaut
     */
    @GetMapping(path = "/drones")
    public @ResponseBody Iterable<Drone> getAllDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        return DroneManager.getAllDrones();
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
    @GetMapping(path = "/drones/{id}/deactivate")
    public @ResponseBody
    Drone deactivateDrone(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        return updateDroneStateAttribute(id, DroneState.RECHARGING);
    }
    @GetMapping(path = "/drones/{id}/activate")
    public @ResponseBody
    Drone activateDrone(@PathVariable("id") int id, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        return updateDroneStateAttribute(id, DroneState.AVAILABLE);
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
    public Drone updateDroneStateAttribute(int id, DroneState newState) {
        Drone zeDrone = DroneManager.getDroneById(id);
        if (zeDrone != null) {
            zeDrone.setStatus(newState);
            return zeDrone;
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone #" + id + " not found");
    }

    @GetMapping({"/drones/totalrecall", "/drones/recall"})
    public @ResponseBody String emergencyRecallAllDeliveringDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        DroneManager.recallAllActiveDrones();
        return "Done."; // 200
    }

    /**
     * Ajouter un nouveau Drone à la HashMap
     *
     * @param drone le Drone a ajouter
     *
     * @return le Drone ajouté
     */
    @PostMapping("/drones")
    public @ResponseBody
    Drone addNewDrone(@RequestBody Drone drone, HttpServletRequest request) throws Exception {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
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
     * @return la HashMap (sera donc un Array vide, servant de "preuve")
     */
    @RequestMapping ("/drones/reset")
    public @ResponseBody Iterable<Drone> removeAllDrones(HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
        DroneManager.resetDrones();
        DroneManager.resetDronesTelemetry();
        return DroneManager.getAllDrones();
    }

    // TODO ajouter une route pour PUT un Array de Drone --> remplacer la base
    @RequestMapping(path = "/drones", method = RequestMethod.PUT)
    public @ResponseBody Iterable<Drone> setWholeDroneDatabase(@RequestBody Iterable<Drone> drones, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())));
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
        Drone drone = DroneManager.getOneAvailableDrone();
        return DroneManager.assignOrderIdToDroneById(order, drone.getId(), FLEET_SERVICE_URL);
    }

    // todo ajouter une route PUT juste pour init les tests ?
    @PostMapping("/drones/{id}/positions")
    public @ResponseBody DronePosition logNewDronePosition(@PathVariable("id") int droneId, @RequestBody DronePosition newPosition, HttpServletRequest request) {
        LOG.warn("Request on " + request.getRequestURI() + ((request.getQueryString() == null) ? "" : ("?" + request.getQueryString())) );
        return DroneManager.logNewDronePosition(droneId, newPosition);
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
        return DroneManager.getDronePositions(droneId);
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
