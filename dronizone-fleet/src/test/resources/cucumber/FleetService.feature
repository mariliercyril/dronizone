Feature: Testing the FleetService API
  User Story 4: Sending a Drone with LOW battery to RECHARGE
  Elena can set a Drone with LOW battery to RECHARGE

  Scenario: Check service's availability
    Given The FleetService API is running on "http://localhost:9004/fleet"
    When a user performs a GET request to "http://localhost:9004/fleet/"
    Then the response code should be 200

  # Scenario: Get a Drone contained in the Database
    # Given The FleetService API is running on "http://localhost:9004/fleet"
    # And The Database contains a Drone with ID 69
    # When a user performs a GET request to "http://localhost:9004/fleet/69"
    # When the user performs a GET request to "http://localhost:9004/fleet/69"
    # Then the response code should be 200
    # And the response's content should be the Drone with ID 69

  # Scenario: API Testing

    # Given The FleetService API is running on "http://localhost:9004/fleet" and contains a Drone with ID 4
    # When And Elena performs a GET request to "http://localhost:9004/fleet/4"
    # Then The response should be 200
    # And the Drone with ID 4 should be returned