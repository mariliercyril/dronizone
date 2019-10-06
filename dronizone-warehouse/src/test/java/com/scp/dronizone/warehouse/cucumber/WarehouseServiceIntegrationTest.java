package com.scp.dronizone.warehouse.cucumber;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber", plugin = {"pretty"})
public class WarehouseServiceIntegrationTest {

}

