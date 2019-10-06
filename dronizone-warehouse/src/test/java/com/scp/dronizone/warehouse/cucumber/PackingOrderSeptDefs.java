package com.scp.dronizone.warehouse.cucumber;



import com.scp.dronizone.common.entity.Item;
import com.scp.dronizone.common.entity.Order;
import com.scp.dronizone.common.entity.OrderManager;
import com.scp.dronizone.common.entity.Warehouse;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PackingOrderSeptDefs {
    Item item;
    Order order;
    List<Order> orders;

    @Given("^an item with id \"([^\"]*)\" in the warehouse$")
    public void an_item_with_id_in_the_warehouse(String itemId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        item = new Item(itemId);
        Warehouse.addItem(item);

    }

    @Given("^an order with id \"([^\"]*)\"$")
    public void an_order_with_id(String idOrder) throws Exception {
        order = Warehouse.createOrder(item.getIdItem());
        OrderManager.addOrder(order);
    }

    @When("^: Klaus goes to the url warehouse/orders$")
    public void roger_order_the_item() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Order>> response = restTemplate.exchange("http://localhost:" + 9002 + "/warehouse/orders", HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>(){});
        orders = response.getBody();

    }

    @Then("^: The server will respond with (\\d+) order with id \"([^\"]*)\"$")
    public void a_new_order_with_the_item_has_been_added(int nbOrder, String idOrder) throws Exception {
        assertEquals(orders.size(),nbOrder);
        assertEquals(orders.get(0).getIdOrder(),idOrder);
    }


}
