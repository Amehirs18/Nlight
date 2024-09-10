package fyers.pages;

import cap.common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;


public class BlogPage extends BasePage {

    public BlogPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//h1[.='Blogs']")
    protected WebElement elmntBlogsHeader;

    @FindBy(how = How.XPATH, using = "//a[contains(@class,'row blog-first')]//div[@class='blog-discs']/h3")
    protected WebElement elmntFirstBlogLink;

    @FindBy(how = How.XPATH, using = "//a[contains(.,'Back')]/following-sibling::h2[@class='text-left']")
    protected WebElement elmntBlogHeaderName;

    public void clickBlogLinkInBlogPage() {
        waitForSeconds(2);
        System.out.println("---> click Blog Link In Blog Page...");
        waitForElement(elmntBlogsHeader);
        waitForElement(elmntFirstBlogLink);
        click(elmntFirstBlogLink);
    }

    public boolean verifyFirstBlogContent() {
        waitForSeconds(2);
        System.out.println(">>-----> verify First Blog Content::");
        attachStepLog("Blog-Name", elmntBlogHeaderName.getText());
        return verifyElement(waitForElement(elmntBlogHeaderName));
    }


}



