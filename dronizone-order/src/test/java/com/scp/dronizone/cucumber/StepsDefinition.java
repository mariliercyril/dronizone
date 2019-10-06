package com.scp.dronizone.cucumber;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.scp.dronizone.common.entity.Item;
import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import com.scp.dronizone.order.OrderController;
import com.scp.dronizone.order.OrderService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class StepsDefinition {
    OrderController orderController = new OrderController();
    WireMockServer wireMockServer = new WireMockServer(9876);
    CloseableHttpClient httpClient = HttpClients.createDefault();
    boolean setupDone = false;

    @Before()
    public void setUp() {
        Warehouse.resetItemList();
        OrderManager.resetOrders();
        if (!setupDone) {
            setupDone = true;
            wireMockServer.start();

        }
    }

    @Given("^: an item with id (\\w+) in the warehouse$")
    public void an_item_with_id_in_the_warehouse(String itemId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        Item item = new Item(itemId);
        Warehouse.addItem(item);

        HttpGet request = new HttpGet("http://localhost:" + 9001 + "/order/items");
        request.addHeader("content-type", "application/json");
        HttpResponse httpResponse = httpClient.execute(request);
        InputStream responseStream = httpResponse.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();

        System.out.println(responseString);

//        HttpGet request = new HttpGet("http://localhost:" + 9002 + "/warehouse/addItem?itemId=" + itemId);
//        request.addHeader("content-type", "application/json");
//        HttpResponse httpResponse = httpClient.execute(request);
    }

    @When("^: Roger order the item (\\w+)$")
    public void roger_order_the_item(String itemId) throws Exception {
        // Write code here that turns the phrase above into concrete actions


        HttpGet request = new HttpGet("http://localhost:" + 9001 + "/order/create?id=" + itemId);
        request.addHeader("content-type", "application/json");
        HttpResponse httpResponse = httpClient.execute(request);
        InputStream responseStream = httpResponse.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();

        System.out.println(responseString);

        orderController.createOrder(itemId);

    }

    @Then("^: a new order with the item (\\w+) has been added$")
    public void a_new_order_with_the_item_has_been_added(String itemId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<Order> orders = OrderManager.getOrders();
        assertEquals("number of orders : ", 1, orders.size());
        assertEquals("id of the order : ", "125", orders.get(0).getItems().get(0).getIdItem());
    }


    @When("^: Roger goes to the url \"([^\"]*)\"$")
    public void roger_goes_to_the_url(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^: The server will respond with an item with id (\\w+)$")
    public void the_server_will_respond_with_an_item_with_id(String arg1) throws Exception {
        List<Item> items = orderController.browseItem();
        assertEquals("The warehouse has one item", 1, items.size());
        assertEquals("The item has the id given in parameter", arg1, items.get(0).getIdItem());

    }

}
