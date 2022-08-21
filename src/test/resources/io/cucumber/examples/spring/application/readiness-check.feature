Feature: Service API is ready

  Scenario: Service Readiness Check
    Given the readiness endpoint is called
    Then the readiness response is positive
