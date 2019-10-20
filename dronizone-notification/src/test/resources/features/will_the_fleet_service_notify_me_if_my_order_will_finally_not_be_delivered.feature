Feature: Will the Fleet Service notify me if my order will finally not be delivered?

  Scenario: Testing abandonment notifications to a customer
    Given I am "MR" Alexandre "Grothendieck"
     When the drone assigned to deliver my order 86 is no longer able to come to the address "rue de la cohomologie cristalline"
     Then the Fleet Service notifies me that my order 86 will finally not be delivered due to "bad weather conditions"
