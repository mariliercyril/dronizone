package com.scp.dronizone.cucumber;

import com.scp.dronizone.order.OrderService;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//@SpringBootTest(classes= OrderService.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
public class SpringIntegrationTest {

    @Before
    public void setup_cucumber_spring_context(){
    }
}