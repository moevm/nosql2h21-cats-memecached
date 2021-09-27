package info.moevm.se.nosqlcatsmemecachedautotests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/info.moevm.se.nosqlcatsmemecachedautotests.features"
)
public class RunCucumberTests extends AbstractTestNGCucumberTests {
}
