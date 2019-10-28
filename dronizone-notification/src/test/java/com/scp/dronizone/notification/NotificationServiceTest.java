package com.scp.dronizone.notification;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;

/**
 * The {@code NotificationServiceTest} class allows JUnit to run Cucumber tests
 * as consumers of the <b>notification</> service.
 * 
 * @author cmarilier
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = "pretty")
public class NotificationServiceTest {}
