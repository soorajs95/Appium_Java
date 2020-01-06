package objects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @AndroidFindBy(id = "single_fragment_toolbar_wordmark")
    MobileElement homePageLogo;

    public static AppiumDriver driver;

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void verifyHomePageLogo() {
        Assert.assertTrue(homePageLogo.isDisplayed());
    }
}
