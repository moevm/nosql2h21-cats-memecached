package info.moevm.se.nosqlcatsmemecachedautotests.page.object;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage {
    private static final long TIMEOUT_IN_SECONDS = 10;

    protected WebDriver driver;

    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        webElement.click();
    }

    protected void sendKeys(WebElement webElement, String message) {
        waitForElementIsVisible(webElement);
        webElement.clear();
        webElement.sendKeys(message);
    }

    protected String getText(WebElement webElement) {
        waitForElementIsVisible(webElement);
        return webElement.getText();
    }

    protected void waitForElementToBeClickable(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS))
            .until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementIsVisible(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_IN_SECONDS))
            .until(ExpectedConditions.visibilityOf(element));
    }
}
