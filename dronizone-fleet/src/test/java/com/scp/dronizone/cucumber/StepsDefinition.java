package com.scp.dronizone.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.scp.dronizone.common.entity.*;
import com.scp.dronizone.fleet.FleetController;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StepsDefinition {
    FleetController fleetController = new FleetController();
    WireMockServer wireMockServer = new WireMockServer(9874);   // 9004 étant le port du Service
    CloseableHttpClient httpClient = HttpClients.createDefault();
    boolean setupDone = false;
    ObjectMapper objectMapper = new ObjectMapper();

    String url = null;
    HttpResponse httpResponse = null;

    @Before()
    public void setUp() {
        // Vider la "BD" de Drones
        DroneManager.resetDrones(); // useless car pas le même que le Service
        // Reset les valeurs partagées
        url = null;
        httpResponse = null;



        // lancer wireMockServer
        if (!setupDone) {
            setupDone = true;
            wireMockServer.start();
        }
    }

    @Given("The FleetService API is running on {string}")
    public void the_FleetService_API_is_running_on(String serviceUrl) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        url = serviceUrl;
        HttpGet request = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(request);
        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();

        System.out.println("responseStatusCode: " + responseStatusCode);
        Assert.assertEquals(200, responseStatusCode);
    }

    @When("a user performs a GET request to {string}")
    public void a_user_performs_a_GET_request_to(String serviceUrl) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        url = serviceUrl;
        httpResponse = httpClient.execute(new HttpGet(url));
    }

    @Then("the response code should be {int}")
    public void the_response_code_should_be(int expectedStatusCode) {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(expectedStatusCode, httpResponse.getStatusLine().getStatusCode());
    }

    /* todo faire fonctionner
    @And("The Database contains a Drone with ID {int}")
    public void theDatabaseContainsADroneWithID(int droneId) throws Exception {
        String postUrl = "http://localhost:9004/fleet/new";

        // Adding the Drone to the DB
        Drone drone = new Drone();
        drone.setId(droneId);
        // DroneManager.registerNewDrone(drone);

        HttpPost postRequest = new HttpPost(postUrl);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", drone.getId().toString()));
        params.add(new BasicNameValuePair("batteryState", drone.getBatteryState().toString()));
        params.add(new BasicNameValuePair("status", drone.getStatus().toString()));
        postRequest.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(postRequest);

        System.out.println(response.getStatusLine().getStatusCode());
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        //HttpEntity<String> request = new HttpEntity<String>(drone.toString(), headers);
        // ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(postUrl, request, String.class);

        String getAllUrl = "http://localhost:9004/fleet/all";
        CloseableHttpClient getAllClient = HttpClients.createDefault();
        objectMapper.readValue(getAllClient.execute());

        Assert.assertTrue(DroneManager.getAllDrones().contains(drone));
    }

    @And("the response's content should be the Drone with ID {int}")
    public void theResponseSCotentShouldBeTheDroneWithID(int droneId) throws IOException {
        Drone resultDrone = objectMapper.readValue(httpResponse.getEntity().getContent(), Drone.class);
        System.out.println(" \n\n\n+++++++++++++++++++++++\n\n\n" + resultDrone);
        Assert.assertEquals(droneId, java.util.Optional.ofNullable(resultDrone.getId()));
    } // */
}
