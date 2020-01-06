package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objects.*;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class MainActivitySteps {

    public BasePage basePage = Hooks.basePage;
    public HomePage homePage = Hooks.homePage;

    public Logger logger = Logger.getLogger(MainActivitySteps.class);

    @Given("The user is on wikipedia home screen")
    public void theUserIsOnWikipediaHomeScreen() {
        try {
            homePage.verifyHomePageLogo();
        } catch (Exception e) {
            logger.fatal("Wikipedia logo not displayed" + e);
            Assert.fail("ERROR : Wikipedia logo not displayed" + e);
        }
    }
}