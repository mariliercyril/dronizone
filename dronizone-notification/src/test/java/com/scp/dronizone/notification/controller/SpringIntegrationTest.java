package com.scp.dronizone.notification.controller;

import com.scp.dronizone.notification.NotificationService;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes= NotificationService.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
public class SpringIntegrationTest {

    @Before
    public void setup_cucumber_spring_context(){
    }
}