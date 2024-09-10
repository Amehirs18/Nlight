package fyers.steps;


import cap.utilities.TestDataUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import fyers.PageInitializer;

import java.util.List;

public class HomeSteps {

    private PageInitializer pageInitializer;

    public HomeSteps(PageInitializer pageInitializer) {
        this.pageInitializer = pageInitializer;
    }

    @Given("I am on Fyers Application")
    public void iAmOnFyersApplication() {
        pageInitializer.homePage.visitFyersApplicationURL();
        Assert.assertTrue(pageInitializer.homePage.waitForFyersHomePage());
    }

    @When("I click {string} under Products menu")
    public void iClickUnderProductsMenu(String strSubTab) {
        pageInitializer.homePage.clickSubMenuUnderMainTab("PRODUCTS", TestDataUtil.getValue(strSubTab));
    }

    @Then("I should redirects to the Fyers App page")
    public void iShouldRedirectsToTheFyersAppPage() {
        Assert.assertTrue(pageInitializer.homePage.verifyAppsPage());
    }

    @And("I move to the {string} page under Media menu")
    public void iMoveToThePageUnderMediaMenu(String strSubTab) {
        pageInitializer.homePage.clickSubMenuUnderMainTab("MEDIA", TestDataUtil.getValue(strSubTab));
    }

    @When("I open a blog from Blogs Feature section")
    public void iOpenABlogFromBlogsFeatureSection() {
        pageInitializer.blogPage.clickBlogLinkInBlogPage();
    }

    @Then("I should see Blog content page")
    public void iShouldSeeBlogContentPage() {
        Assert.assertTrue(pageInitializer.blogPage.verifyFirstBlogContent());
    }

    @And("I move to Sign In page")
    public void iMoveToSignInPage() {
        pageInitializer.homePage.clickSignInButton();
    }

    @When("I enter un-registered {string}")
    public void iEnterUnRegistered(String strMobileNo) {
        pageInitializer.homePage.clickLoginWithMobileNumberLink();
        pageInitializer.homePage.enterMobileNumber(TestDataUtil.getValue(strMobileNo));
        pageInitializer.homePage.clickContinueButton();

    }

    @Then("I should see {string} message")
    public void iShouldSeeMessage(String strErrorMessage) {
        pageInitializer.homePage.verifyErrorMessage(strErrorMessage);
    }
}
