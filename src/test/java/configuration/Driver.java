package configuration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.core.api.Scenario;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import steps.Hooks;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Driver {

    private static final Logger logger = Logger.getLogger(Driver.class);

    public static AppiumDriver getAndroidDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", Hooks.setValue("and_device_name"));
            caps.setCapability("udid", Hooks.setValue("and_udid"));
            caps.setCapability("platformName", Hooks.setValue("and_rd_platform"));
            caps.setCapability("platformVersion", Hooks.setValue("and_platform_version"));
            caps.setCapability("automationName", Hooks.setValue("and_automation_name"));
            File app = new File("src/test/resources/" + Hooks.setValue("and_app"));
            caps.setCapability("app", app.getAbsolutePath());
//        caps.setCapability("appPackage", "tv.obs.ovp.debug");
//        caps.setCapability("appActivity", "com.mediamonks.obs.ui.screens.splash.SplashActivity");
            return new AppiumDriver(new URL(Hooks.setValue("appium_server")), caps);
        } catch (Throwable err) {
            logger.fatal("Android Driver is not initialized" + ": " + err);
            Assert.fail("ERROR : " + "Android Driver is not initialized" + err);
        }
        return null;
    }

    public static AppiumDriver getAndroidSimulatorDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", Hooks.setValue("asim_device_name"));
            caps.setCapability("udid", Hooks.setValue("asim_udid"));
            caps.setCapability("platformName", Hooks.setValue("asim_platform_name"));
            caps.setCapability("platformVersion", Hooks.setValue("asim_platform_version"));
            caps.setCapability("automationName", Hooks.setValue("asim_automation_name"));
            File app = new File("src/test/resources/" + Hooks.setValue("asim_app"));
            caps.setCapability("app", app.getAbsolutePath());
            return new AppiumDriver(new URL(Hooks.setValue("appium_server")), caps);
        } catch (Throwable err) {
            logger.fatal("Android Simulator Driver is not initialized" + ": " + err);
            Assert.fail("ERROR : " + "Android Simulator Driver is not initialized" + err);
        }
        return null;
    }

    public static IOSDriver getIOSSimulatorDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", Hooks.setValue("isim_device_name"));
            caps.setCapability("udid", Hooks.setValue("isim_udid"));
            caps.setCapability("platformName", Hooks.setValue("isim_platform_name"));
            caps.setCapability("platformVersion", Hooks.setValue("isim_platform_version"));
            caps.setCapability("automationName", Hooks.setValue("isim_automation_name"));
            File app = new File("src/test/resources/" + Hooks.setValue("isim_app"));
            caps.setCapability("app", app.getAbsolutePath()); //Supports only .zip/.app
            return new IOSDriver(new URL(Hooks.setValue("appium_server")), caps);
        } catch (Throwable err) {
            logger.fatal("iOS Simulator Driver is not initialized" + ": " + err);
            Assert.fail("ERROR : " + "iOS Simulator Driver is not initialized" + err);
        }
        return null;
    }

    public static AppiumDriver getBrowserstackDriver(Scenario scenario) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserstack.local", Hooks.setValue("bs_local_testing"));
            caps.setCapability("device", Hooks.setValue("bs_device"));
            caps.setCapability("name", scenario.getName());
            caps.setCapability("app", Hooks.setValue("MyApp")); //Supports .ipa/.app/.zip for iOS - bcoz it uses real devices internally
//            File app = new File("src/test/resources/" + Hooks.setValue("bs_app"));
//            caps.setCapability("app", app.getAbsolutePath());
            return new AppiumDriver(new URL(Objects.requireNonNull(setURL())), caps);
        } catch (Throwable err) {
            logger.fatal("Browserstack Driver is not initialized" + ": " + err);
            Assert.fail("ERROR : " + "Browserstack Driver is not initialized" + err);
        }
        return null;
    }

    private static String setURL() {
        switch (Hooks.setValue("end_point").toLowerCase().trim()) {
            case "saucelabs":
                return "http://" + Hooks.readConfig("sauce_username") + ":" + Hooks.readConfig("sauce_accesskey")
                        + "@ondemand.eu-central-1.saucelabs.com/wd/hub";
            case "browserstack":
                return "https://" + Hooks.readConfig("browserstack_username") + ":" + Hooks.readConfig(
                        "browserstack_accesskey") + "@hub-cloud.browserstack.com/wd/hub";
        }
        return null;
    }
}
