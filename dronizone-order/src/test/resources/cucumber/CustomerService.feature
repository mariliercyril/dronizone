Feature: I don't know
  Scenario: Roger want to know available items
    Given : an item with id 1337 in the warehouse
    When : Roger goes to the url "orders/items"
    Then : The server will respond with an item with id 1337

  Scenario: Roger orders an item
    Given : an item with id 1337 in the warehouse
    When : Roger orders the item 1337
    Then : a new order with the item 1337 has been added