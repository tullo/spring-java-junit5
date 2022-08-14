Feature: See Messages

  Scenario Outline: See another user's messages
    Given there is a user
    And the user has posted the message <content>
    When I visit the page for the User
    Then I should see <content>
    Examples:
      | content              |
      | "this is my message" |
