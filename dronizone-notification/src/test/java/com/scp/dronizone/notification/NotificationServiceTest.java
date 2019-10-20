package com.scp.dronizone.notification;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;

/**
 * The {@code NotificationServiceTest} class allows JUnit to run the Cucumber tests of as service.
 * 
 * @author cmarilier
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = "pretty")
public class NotificationServiceTest {}
