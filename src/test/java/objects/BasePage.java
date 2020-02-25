package objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;
import steps.Hooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    public static AppiumDriver driver;

    @AndroidFindBy(id = "logo")
    protected MobileElement logo;

    public BasePage() {

    }

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int startHeight = (int) (size.height * 0.5);
        int startWidth = (int) (size.width * 0.5);
        int endHeight = (int) (size.height * 0.25);
        int endWidth = (int) (size.width * 0.5);
        new TouchAction(driver).press(PointOption.point(startWidth, startHeight)).waitAction()
                .moveTo(PointOption.point(endWidth, endHeight)).release().perform();
    }

    public void scrollUp() {
        Dimension size = driver.manage().window().getSize();
        int startHeight = (int) (size.height * 0.5);
        int startWidth = (int) (size.width * 0.5);
        int endHeight = (int) (size.height * 0.8);
        int endWidth = (int) (size.width * 0.5);
        new TouchAction(driver).press(PointOption.point(startWidth, startHeight)).waitAction()
                .moveTo(PointOption.point(endWidth, endHeight)).release().perform();
    }

    public void waitAndClick() {

    }

    ;

}
