Feature: Service API is alive

  Scenario: Liveliness Check
    Given the liveliness endpoint is called
    Then the liveliness response is positive
