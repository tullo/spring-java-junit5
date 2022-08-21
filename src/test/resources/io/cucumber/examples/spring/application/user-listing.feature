Feature: Service API

  Background: User generates token for Authorisation
    Given User is authorized

  Scenario: Authorized user lists users.
    Given A list of users are available
    When A list of users are available from DAF
    Then The lists are equal in size and content
