package info.moevm.se.nosqlcatsmemecachedautotests.page.element;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CatList {
    protected final WebDriver driver;

    public CatList(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> list() {
        return new WebDriverWait(driver, Duration.ofSeconds(3000))
            .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("item-wrapper")));
    }
}
