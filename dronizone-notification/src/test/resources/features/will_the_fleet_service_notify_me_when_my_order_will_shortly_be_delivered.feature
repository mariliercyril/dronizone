Feature: Will the Fleet Service notify me when my order will shortly be delivered?

  Scenario Outline: Testing arrival notifications to a customer
    Given I am <customerGender> <customerName>
     When the drone delivering my order <orderId> will arrive shortly at the address "<deliveryAddress>"
     Then the Fleet Service notifies me that my order <orderId> vill shortly be delivered

    Examples:
      | customerGender | customerName | orderId | deliveryAddress |
      |             MR |         Abel |      27 |   Norway street |
      |             MR |       Galois |      21 |    group street |
      |             MR |      Riemann |      40 |  surface street |
