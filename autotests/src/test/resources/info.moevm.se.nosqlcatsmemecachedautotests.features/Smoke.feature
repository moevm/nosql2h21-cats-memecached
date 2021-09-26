Feature: Main page tests

  Scenario: Index page contains a list of cats
    Given I open index page
    When I use default filters
    Then Cat list is not empty