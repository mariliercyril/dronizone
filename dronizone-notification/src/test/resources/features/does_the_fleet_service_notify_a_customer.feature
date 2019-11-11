Feature: Does the Fleet Service notify a customer?

  Background:
    Given I a customer with an order

  Scenario: Testing if a customer is notified when its order will finally not be delivered
      And I am "MR" Alexandre "Grothendieck" and my customer number is equal to 1
     When the drone assigned to deliver my order 86 at the address "rue de la cohomologie cristalline" is no longer able to fulfill its mission
     Then due to "bad weather conditions" the Fleet Service notifies me that my order 86 will finally not be delivered

  Scenario Outline: Testing if a customer is notified when its order will shortly be delivered
      And I am <customerGender> <customerName> and my customer number is <customerId>
     When the drone delivering my order <orderId> will arrive shortly at the address <deliveryAddress>
     Then the Fleet Service notifies me that my order <orderId> will shortly be delivered and status code of 201

    Examples:
      | customerGender | customerName | customerId | orderId | deliveryAddress |
      |             MR |         Abel |          2 |      26 |   Norway street |
      |             MR |       Galois |          3 |      20 |    group street |
      |             MR |      Riemann |          4 |      39 |  surface street |
