package info.moevm.se.nosqlcatsmemecachedautotests.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;

public class ThenStep extends AbstractStep {
    @Then("Cat list is not empty")
    public void catListIsNotEmpty() {
        assertThat(indexPage.catList().list().size()).isNotZero();
    }
}
