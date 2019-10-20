package com.scp.dronizone.warehouse.cucumber;

import com.scp.dronizone.warehouse.service.WarehouseService;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//@SpringBootTest(classes= WarehouseService.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
public class SpringIntegrationTest {

    @Before
    public void setup_cucumber_spring_context(){
    }
}