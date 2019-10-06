Feature: Does the drone assigned to my delivery notify me when it is no longer able to fulfill its mission?

  Scenario: Testing abandonment notifications to a customer
    Given I am a customer
      And my name is "Grothendieck"
    When the drone assigned to deliver my order 86 is no longer able to fulfill its mission
    Then it notifies me that my order 8 will no longer be delivered
