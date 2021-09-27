package info.moevm.se.nosqlcatsmemecachedautotests.steps;

import info.moevm.se.nosqlcatsmemecachedautotests.hooks.CucumberHook;
import info.moevm.se.nosqlcatsmemecachedautotests.page.object.IndexPage;
import org.openqa.selenium.WebDriver;

public abstract class AbstractStep {
    protected final WebDriver driver;
    protected final IndexPage indexPage;

    protected AbstractStep() {
        driver = CucumberHook.getDriver();
        indexPage = new IndexPage(driver);
    }
}
