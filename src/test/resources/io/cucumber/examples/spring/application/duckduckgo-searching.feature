Feature: Wikipedia Searching

  Scenario: direct search article
    Given Enter search term 'Cucumber'
    When Do search
    Then Then Single result is shown for 'Cucumber'
