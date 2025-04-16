Feature: Login tests
#  @smoke
  Scenario Outline: Test login
    Given user is on the login page
    When user enters username "<username>"
    And user enters password "<password>"
    And clicks the submit button
    Then user should see a success message "<message>"

    Examples:
      | username | password     | message                           |
      | student  | Password123  | Congratulations student. You      |
      | invalid  | wrongPass    | Invalid username or password      |
#      | invalid  | wrongPass    | Invalid username or password      |
#      | invalid  | wrongPass    | Invalid username or password      |
#      | invalid  | wrongPass    | Invalid username or password      |
