Feature: register a user on https://membership.basketballengland.co.uk/NewSupporterAccount

  Scenario: Successful registration
    Given That I am on the correct start page on "chrome"
    When I fill in DOB as "14/02/1999"
    And I fill in name as "Torbjörn Tester"
    And I fill in emails as "TorbjornTestare@gmail.com"
    And I fill in password as "Lösenord123"
    And I fill in terms&conditions as "clicked"
    Then I Submit and verify

    #Behövde DOB steget då den inte gav fel utan att det fältet var ifyllt
    #Inte helt nöjd med namnet "Category" men kom ej på något bättre
    #Hade hoppats på en bättre lösnining än mina extra stepdefs men blev spaghetti av variabler annars
    #(vilket såg dumt ut i outline, ville ha alla alla fel i en outline)
  Scenario Outline: Registration fail iterations 18+
    Given That I am on the correct start page on "<Browser>"
    When I fill in DOB as "14/02/1999"
    And I fill in <Category> as "<Input>"
    Then I Submit and verify <error>

    Examples:
      | Browser | Category             | Input    | error                         |
      | chrome  | name                 | Torbjörn | missing last name             |
      | chrome  | mismatching password | Test123  | passwords not matching        |
      | chrome  | terms&conditions     | none     | terms&conditions not accepted |
      | firefox | name                 | Torbjörn | missing last name             |
      | firefox | mismatching password | Test123  | passwords not matching        |
      | firefox | terms&conditions     | none     | terms&conditions not accepted |
      | edge    | name                 | Torbjörn | missing last name             |
      | edge    | mismatching password | Test123  | passwords not matching        |

