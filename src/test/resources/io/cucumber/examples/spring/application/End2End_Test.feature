Feature: ToolsQA Book Store API
  Description: The purpose of these tests are to cover the End to End happy paths for customer.

  Book Store Swagger URL : https://bookstore.toolsqa.com/swagger/

  Background: User generates token for Authorisation
    Given User is authorized

  Scenario: Authorized user adds and removes a book.
    Given A list of books are available
    When User adds a book to my reading list
    Then The book is added
    When User removes a book from my reading list
    Then The book is removed
