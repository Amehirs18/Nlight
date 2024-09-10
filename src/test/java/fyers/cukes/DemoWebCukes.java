package fyers.cukes;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/features/Regression"},
        monochrome = true,
        tags = "@Fyers_WebApp",
        glue = {"fyers"},
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/webapp/index.html",
                "json:reports/webapp/cucumber.json"
        })
// clean test -Dapp=demo -Drunner=DemoWebCukes -Dexecution_type=browser -Denv=QA
public class DemoWebCukes extends AbstractTestNGCucumberTests {

}
