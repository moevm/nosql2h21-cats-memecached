package info.moevm.se.nosqlcatsmemecachedautotests.steps;

import io.cucumber.java.en.Given;

public class GivenStep extends AbstractStep {
    @Given("I open index page")
    public void openIndexPage() {
        indexPage.open();
    }
}
