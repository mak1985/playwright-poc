Feature: Swag Labs Login
@smoke
  Scenario Outline: Login with valid and invalid credentials
    Given I am on the Swag Labs login page
    When I login with username "<username>" and password "<password>"
    Then I should see the result "<result>"

    Examples:
      | username      | password     | result                |
      | standard_user | secret_sauce | Success               |
#      | locked_out    | secret_sauce | Epic sadface: ...     |
#      | invalid_user  | wrong_pass   | Epic sadface: ...     |