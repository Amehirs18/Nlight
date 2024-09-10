package fyers.pages;

import cap.common.BasePage;
import cap.utilities.DateUtil;
import cap.utilities.TestDataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    protected String strMenuTabLocator = new StringBuilder().append("//a[contains(@id,'navbarDropdown')][contains(.,'")
            .append("<<TAB>>").append("')]").toString();
    protected String strSubMenuTabLocator = new StringBuilder().append("//div/h6[contains(text(),'")
            .append("<<SUBTAB>>").append("')]").toString();


//h1[text()='Your Gateway to Investing!']
//a/img[@alt='Logo']

//a[text()='SIGN IN']
//a[contains(@id,'navbarDropdown')][contains(.,'MEDIA')]
//a[contains(@id,'navbarDropdown')][contains(.,'PRODUCTS')]

//div/h6[contains(text(),'Fyers App')]

//h1[contains(.,'Most Complete')]

//div/h6[contains(text(),'Blogs')]

//h1[.='Blogs']
//a[contains(@class,'row blog-first')]//div[@class='blog-discs']/h3


    @FindBy(how = How.XPATH, using = "//h1[text()='Your Gateway to Investing!']")
    protected WebElement elmntInvestingHeader;

    @FindBy(how = How.XPATH, using = "//a/img[@alt='Logo']")
    protected WebElement elmntApplicationLogo;

    @FindBy(how = How.XPATH, using = "//a[text()='SIGN IN']")
    protected WebElement elmntSignInLink;

    @FindBy(how = How.XPATH, using = "//h1[contains(.,'Most Complete')]")
    protected WebElement elmntMostCompleteHeader;

    @FindBy(how = How.CSS, using = "#client-id-form")
    protected WebElement elmntClientIDLoginForm;

    @FindBy(how = How.CSS, using = "#back_to_mobile_login")
    protected WebElement elmntLoginWithMobileNumber;

    @FindBy(how = How.CSS, using = "#mobile-code")
    protected WebElement txtMobileNumber;

    @FindBy(how = How.CSS, using = "#mobileNumberSubmit")
    protected WebElement btnContinue;

    @FindBy(how = How.CSS, using = "#fy-notification-title")
    protected WebElement elmntInvalidMobileNumberErrorMessage;

    @FindBy(how = How.CSS, using = "#fy-notification-desc")
    protected WebElement elmntErrorMessageUnderInvalidMobileNumberMessageField;

    public void visitFyersApplicationURL() {
        visit(TestDataUtil.getValue("&URL&"));

    }


    public boolean waitForFyersHomePage() {
        return verifyElement(waitForElement(elmntInvestingHeader)) &&
                verifyElement(waitForElement(elmntApplicationLogo));
    }

    public void clickSubMenuUnderMainTab(String strTab, String strSubTab) {
        System.out.println("--> TAB:" + strTab);
        System.out.println("--> SUB-TAB:" + strSubTab);
        System.out.println("---> TAB Locator: " + strMenuTabLocator.replace("<<TAB>>", strTab));
        System.out.println("---> SUB-TAB Locator: " + strSubMenuTabLocator.replace("<<SUBTAB>>", strSubTab));
        Actions act = new Actions(driver);
        act.moveToElement(waitForElement(By.xpath(strMenuTabLocator.replace("<<TAB>>", strTab))));
        act.moveToElement(waitForPresenceOfElement(By.xpath(strSubMenuTabLocator.replace("<<SUBTAB>>", strSubTab))));
        act.click().build().perform();
        System.out.println("---> After mouse hovering...");
    }

    public boolean verifyAppsPage() {
        waitForSeconds(2);
        System.out.println("verify App Page");
        attachStepLog("URL", driver.getCurrentUrl());
        return verifyElement(waitForElement(elmntMostCompleteHeader));
    }


    public void clickSignInButton() {
        waitForElement(elmntSignInLink);
        click(elmntSignInLink);
    }

    public void clickLoginWithMobileNumberLink() {
        waitForElement(elmntClientIDLoginForm);
        waitForElement(elmntLoginWithMobileNumber);
        click(elmntLoginWithMobileNumber);
    }

    public void enterMobileNumber(String strMobileNo) {
        waitForElement(txtMobileNumber);
        enterValue(txtMobileNumber, strMobileNo);
    }

    public void clickContinueButton() {
        waitForElement(btnContinue);
        click(btnContinue);
    }

    public boolean verifyErrorMessage(String strErrorMessage) {
        waitForSeconds(3);
        waitForElement(elmntInvalidMobileNumberErrorMessage).getText();
        waitForElement(elmntErrorMessageUnderInvalidMobileNumberMessageField);
        attachStepLog(strErrorMessage, elmntErrorMessageUnderInvalidMobileNumberMessageField.getText().trim());
        return waitForElement(elmntInvalidMobileNumberErrorMessage).getText().contains(strErrorMessage);
    }
}



