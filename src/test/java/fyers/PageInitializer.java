package fyers;

import cap.utilities.SharedDriver;
import fyers.pages.BlogPage;
import fyers.pages.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageInitializer {
    private WebDriver driver;


    public static Scenario myScenario;
    public static LinkedHashMap<String, String> printTestDataMap = new LinkedHashMap<String, String>();

    public HomePage homePage;
    public BlogPage blogPage;

    public PageInitializer() {
        driver = SharedDriver.getDriver();
        initPages();
    }



    private void initPages() {
        homePage = new HomePage(driver);
        blogPage = new BlogPage(driver);
    }

    @After("@WEB")
    public void afterScenario(Scenario scenario) {
        if (printTestDataMap.size() > 0) {
            for (Map.Entry<String, String> entry : printTestDataMap.entrySet()) {
                scenario.log(entry.getKey().concat(": ").concat(entry.getValue()));
            }
        }

        byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(imageBytes, "image/png", "");

        System.out.println("\n Scenario Outline: " + scenario.getName());
        System.out.println("\n Scenario Status: " + scenario.getStatus());


    }

    //  @Before("not @API and not @Mobile and not @Desktop")
    @Before("@WEB")
    public void before(Scenario scenario1) {
        printTestDataMap.clear();
        myScenario = scenario1;
    }
}
