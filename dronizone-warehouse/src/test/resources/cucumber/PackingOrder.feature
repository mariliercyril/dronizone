Feature: Warehouse packing order

    Background:
        Given an order with id 1

    Scenario: Klaus want to know available items
        When : Klaus goes to the url warehouse/orders
        Then : The server will respond with 1 order with id 1

