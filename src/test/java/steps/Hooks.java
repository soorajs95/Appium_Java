package steps;

import configuration.Driver;
import io.appium.java_client.AppiumDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import objects.BasePage;
import objects.HomePage;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.SessionId;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public AppiumDriver driver;
    public String endpoint = System.getProperty("end_point") == null ? readConfig("end_point") : System.getProperty("end_point");

    public Logger logger = Logger.getLogger("devpinoyLogger");

    public static BasePage basePage;
    public static HomePage homePage;

    @Before
    public void setUp(Scenario scenario) {
        try {
            switch (endpoint.toLowerCase().trim()) {
                case "android":
                    driver = Driver.getAndroidDriver();
                    break;
                case "android_simulator":
                    driver = Driver.getAndroidSimulatorDriver();
                    break;
                case "ios_simulator":
                    driver = Driver.getIOSSimulatorDriver();
                    break;
                case "browserstack":
                    driver = Driver.getBrowserstackDriver(scenario);
                    break;
//                case "docker":
//                    driver = Driver.getDockerDriver();
//                    break;
//                case "ios":
//                    driver = Driver.getIOSDriver();
//                    break;
//                case "saucelabs":
//                    driver = Driver.getSauceDriver();
//                    break;
            }
            logger.info("Driver is initialized");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            initPages();
            logger.info("App is initialized");
        } catch (Throwable err) {
            logger.fatal("App is not initialized: " + err);
            Assert.fail("ERROR : " + "App is not initialized: " + err);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed())
                getScreenshot(scenario);
            if (endpoint.equalsIgnoreCase("saucelabs"))
                updateSauceResults(scenario);
            else if (endpoint.equalsIgnoreCase("browserstack"))
                updateBrowserStackResults(scenario);
            driver.quit();
            System.gc();
            logger.info("Driver Quit");
            LogManager.shutdown();
        } catch (Throwable err) {
            logger.fatal("Cannot quit from driver: " + err);
            Assert.fail("ERROR : " + "Cannot quit from driver: " + err);
        }
    }

    public void initPages() {
        try {
            basePage = new BasePage(driver);
            homePage = new HomePage(driver);
            BasicConfigurator.configure();
            logger.setAdditivity(false);
            logger.info("Pages are initialized");
        } catch (Throwable err) {
            logger.fatal("Pages are not initialized" + ": " + err);
            Assert.fail("ERROR : " + "Pages are not initialized" + err);
        }
    }

    private void getScreenshot(Scenario scenario) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String scenarioName = scenario.getName().replace(" ", "");
            FileUtils.copyFile(scrFile, new File("target/screenshot/" + System.getProperty("env") + "/" + scenarioName + java.time.LocalDateTime.now() + ".png"));
            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        } catch (Throwable err) {
            logger.fatal("Failed to generate screenshot: " + err);
            Assert.fail("ERROR : " + "Failed to generate screenshot: " + err);
        }
    }

    private void updateSauceResults(Scenario scenario) {
        String status = scenario.isFailed() ? "failed" : "passed";
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (status));
    }

    private void updateBrowserStackResults(Scenario scenario) {
        try {
            String status = scenario.isFailed() ? "failed" : "passed";
            SessionId session = driver.getSessionId();
            URI uri = new URI("https://" + readConfig("browserstack_username") + ":" + readConfig("browserstack_accesskey") + "@api-cloud.browserstack.com/app-automate/sessions/" + session + ".json");
            HttpPut putRequest = new HttpPut(uri);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add((new BasicNameValuePair("status", status)));
            nameValuePairs.add((new BasicNameValuePair("reason", "Check report for details")));
            putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpClientBuilder.create().build().execute(putRequest);
        } catch (Throwable err) {
            logger.fatal("Results not updated to Browserstack " + ": " + err);
            Assert.fail("ERROR : " + "Results not updated to Browserstack " + err);
        }
    }

    public static String readConfig(String config) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/test/resources/Config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty(config).trim();
    }

    public static String setValue(String property) {
        return System.getProperty(property) == null ? readConfig(property) : System.getProperty(property);
    }
}
