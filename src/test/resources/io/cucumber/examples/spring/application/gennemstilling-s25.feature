Feature: GennemstillingsService S18

  Background: User generates token for Authorisation
    Given User is authorized s18

  @gateway
  Scenario Outline: S18 - EJF -FOO BAR kaldes med obligatoriske parametre
    Given DAF service returnerer en liste med vurderinger for "<PARAMETERS>"
    Given GATEWAY service returnerer en liste med vurderinger for "<PARAMETERS>"
    When Begge lister indeholder vurderinger
    Then Begge lister er lige store og har samme indhold
    Examples:
      | URI          | PARAMETERS                     |
      | /FOO/BAR/BAZ | CPRnr=1234561234               |
      | /FOO/BAR/BAZ | CPRnr=1234561234,Status=qwerty |
      | /FOO/BAR/BAZ | CVRnr=1234561234               |
      | /FOO/BAR/BAZ | CVRnr=1234561234,Status=qwerty |
      | /FOO/BAR/BAZ | POVnr=1234561234               |
      | /FOO/BAR/BAZ | POVnr=1234561234,Status=qwerty |

# mvn clean test -D cucumber.filter.tags="@gateway"
# mvn clean test -D cucumber.filter.tags="not @gateway"