package com.scp.dronizone.cucumber;

import com.scp.dronizone.order.entity.Item;
import com.scp.dronizone.order.entity.Order;
import com.scp.dronizone.order.entity.OrderManager;
import com.scp.dronizone.order.entity.Warehouse;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class StepsDefinition {
    OrderController orderController = new OrderController();
    RestTemplate restTemplate = new RestTemplate();

    String warehouseURL = "http://localhost:9002/warehouse/";
    String orderServiceURL = "http://localhost:9001/orders/";

    @Given("^: an item with id (\\w+) in the warehouse$")
    public void an_item_with_id_in_the_warehouse(String itemId) throws Exception {
        restTemplate.delete(warehouseURL + "items");
        Item item = new Item(itemId);

        Item responseItem = restTemplate.postForObject(warehouseURL + "items", item, Item.class);
        assertEquals("1337", responseItem.getIdItem());

    }

    @When("^: Roger orders the item (\\w+)$")
    public void roger_order_the_item(String itemId) throws Exception {
        Order order = new Order();
        order.addItem(new Item(itemId));

        Order createdOrder = restTemplate.postForObject(orderServiceURL, order, Order.class);
        assertNotNull(createdOrder);


    }

    @Then("^: a new order with the item (\\w+) has been added$")
    public void a_new_order_with_the_item_has_been_added(String itemId) throws Exception {

        ParameterizedTypeReference<List<Order>> ptr = new ParameterizedTypeReference<List<Order>>() {
        };
        ResponseEntity<List<Order>> response = restTemplate.exchange(orderServiceURL, HttpMethod.GET, null, ptr);

        List<Order> orders = response.getBody();

        assertEquals("number of orders : ", 1, orders.size());
        assertEquals("id of the order : ", itemId, orders.get(0).getItems().get(0).getIdItem());
    }


    @When("^: Roger goes to the url \"([^\"]*)\"$")
    public void roger_goes_to_the_url(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("^: The server will respond with an item with id (\\w+)$")
    public void the_server_will_respond_with_an_item_with_id(String arg1) throws Exception {
        ParameterizedTypeReference<List<Item>> ptr = new ParameterizedTypeReference<List<Item>>() {
        };

        ResponseEntity<List<Item>> response= restTemplate.exchange(orderServiceURL+"items", HttpMethod.GET, null, ptr);

        List<Item> items = response.getBody();

        assertEquals("The warehouse has one item", 1, items.size());
        assertEquals("The item has the id given in parameter", arg1, items.get(0).getIdItem());

    }

}
