Feature: Demo_Fyers_Application_Scenarios

  @WEB @Fyers_WebApp
  Scenario Outline: User visits Products page in Fyers Application

    Given I am on Fyers Application
    When I click "<Product_Name>" under Products menu
    Then I should redirects to the Fyers App page
    Examples:
      | Product_Name  |
      | &PRODUCTNAME& |

  @WEB @Fyers_WebApp
  Scenario: User views the Stock market related blogs
    Given I am on Fyers Application
    And I move to the "Blogs" page under Media menu
    When I open a blog from Blogs Feature section
    Then I should see Blog content page

  @WEB @Fyers_WebApp
  Scenario Outline: User enters unregistered mobile number in Sign In page
    Given I am on Fyers Application
    And I move to Sign In page
    When I enter un-registered "<mobile-number>"
    Then I should see "Invalid Mobile Number!" message
    Examples:
      | mobile-number  |
      | &MOBILENUMBER& |