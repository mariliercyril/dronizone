package com.scp.dronizone.cucumber;

import com.scp.dronizone.fleet.entity.Drone;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class StepsDefinition {
    // Variables à transmettre entre les Steps
    // --> /!\ SÉRIEUX RISQUE DE LEAK /!\
    // (si deux tests ont le même @Given et s'exécutent en même temps par exemple, ce qui est notre cas)
    private String url = null;
    private String serviceUrlForTheScenario = null;
    private ResponseEntity<String> responseEntityForTheScenario = null;
    private ResponseEntity<Drone> singleDroneEntityForTheScenario = null;


    @Before() // Before each Scenario
    public void setUp() {
        System.out.println("\n-------------- BEFORE --------------");

        // Reset les valeurs partagées
        // fait automatiquement askip ???
        System.out.println("[AVANT] url --> " + url);
        url = null;
        System.out.println("[APRES] url --> " + url);

        System.out.println("[AVANT] responseEntityForTheScenario --> " + responseEntityForTheScenario);
        responseEntityForTheScenario = null; // fait automatiquement askip
        System.out.println("[APRES] responseEntityForTheScenario --> " + responseEntityForTheScenario);

        // RESET la BD avant chaque scenario
        String resetDbUrlPath = "http://localhost:9004/fleet/drones/reset";
        RestTemplate restTemplate = new RestTemplate();

        System.out.println("La BD est --> "
                + restTemplate.getForEntity(resetDbUrlPath, String.class).getBody());
    }

    /**
     * Instructions à exécuter après chaque scenario
     * Utilisé pour vider la "BD" du Service (avec un "/drones/reset"
     */
    @After // After the end of each Scenario (passed, failed, skipped,
    public void doSomethingAfter(){
        String resetDbUrlPath = "/drones";
        RestTemplate restTemplate = new RestTemplate();

        System.out.println("La BD est --> "
                + restTemplate.getForEntity(serviceUrlForTheScenario + resetDbUrlPath, String.class).getBody());
    }

    @Given("The FleetService API is running on {string}")
    public void the_FleetService_API_is_running_on(String serviceUrl) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        serviceUrlForTheScenario = serviceUrl;
        url = serviceUrl;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(serviceUrl, String.class);
        int responseStatusCode = response.getStatusCodeValue();

        System.out.println("responseStatusCode: " + responseStatusCode);
        Assert.assertEquals(200, responseStatusCode);
    }

    @When("a user performs a GET request to {string}")
    public void a_user_performs_a_GET_request_to(String serviceUrl) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        url = serviceUrl;
        RestTemplate restTemplate = new RestTemplate();
        responseEntityForTheScenario = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(int expectedStatusCode) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(HttpStatus.OK, responseEntityForTheScenario.getStatusCode());
        Assert.assertEquals(200, responseEntityForTheScenario.getStatusCodeValue());
    }

    @And("The Database contains a Drone with ID {int}")
    public void theDatabaseContainsADroneWithID(int droneId) {
        // Pas de persistence, je crée le Drone ici (il faut que la route de POST fonctionne du coup..., ce test est semi-stoopid)
        String postDronePathUrl = "/drones";
        RestTemplate restTemplate = new RestTemplate();
        Drone drone = new Drone(droneId);
        drone.setId(droneId);

        System.out.println("\n\n\n\ndrone:" + drone);

        // Pas de persistence --> on doit "POST" le Drone pour le test
        // todo ? Préparer la BD nécessaire à tous les scenarios dans le @Before ?
        //  Et si un a besoin que la BD soit vide, on lui fait un @Before specific / on le vide durant le test
        singleDroneEntityForTheScenario = restTemplate.postForEntity(
                serviceUrlForTheScenario + postDronePathUrl,
                drone,
                Drone.class);
        Drone returnedDrone = singleDroneEntityForTheScenario.getBody();

        // Check que les bonnes valeurs sont insérées... Ce qui est con car c'est ce que le test d'après va faire aussi...
        Assert.assertEquals(drone.getId(), returnedDrone.getId());
        Assert.assertEquals(drone.getBatteryState(), returnedDrone.getBatteryState());
        Assert.assertEquals(drone.getStatus(), returnedDrone.getStatus());
    }

    @And("the response's content should be the Drone with ID {int}")
    public void theResponseSContentShouldBeTheDroneWithID(int droneId) {
        Drone returnedDrone = singleDroneEntityForTheScenario.getBody();

        Assert.assertEquals(droneId, (int) returnedDrone.getId());
    }
}
