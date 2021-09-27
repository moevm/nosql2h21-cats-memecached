package info.moevm.se.nosqlcatsmemecachedautotests.page.object;

import info.moevm.se.nosqlcatsmemecachedautotests.page.element.CatList;
import org.openqa.selenium.WebDriver;

public class IndexPage extends AbstractPage {

    protected CatList catList;

    public CatList catList() {
        return catList;
    }

    public IndexPage(WebDriver driver) {
        super(driver);
        catList = new CatList(driver);
    }

    public void open() {
        driver.navigate().to("http://localhost:3000/");
    }
}
