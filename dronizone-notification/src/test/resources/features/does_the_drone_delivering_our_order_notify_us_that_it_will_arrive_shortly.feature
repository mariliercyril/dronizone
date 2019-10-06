Feature: Does the drone delivering our order notify us that it will arrive shortly?

  Scenario Outline: Testing arrival notifications to a customer
    Given I am a lazy customer
      And my name is <customerName> and my address is <customerAddress>
    When the drone delivering my order <idOrder> will arrive shortly
    Then it notifies me that my order <idOrder> vill be delivered at my address

    Examples:
      | customerName | customerAddress | idOrder |
      |         Abel |   Norway street |      27 |
      |       Galois |    group street |      21 |
      |      Riemann |  surface street |      40 |
