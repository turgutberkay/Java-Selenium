package util;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import util.DriverFactory;
import util.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.Reporter;

public class ApplicationHooks {

    private DriverFactory driverFactory;
    private WebDriver driver;
    private ConfigReader configReader;
    Properties prop;

    @Before(order = 0)
    public void getProperty() {
        configReader = new ConfigReader();
        prop = configReader.init_prop();
    }

    @Before(order = 1)
    public void launchBrowser() {
        String browserName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
        try {
            int implicitlyWait = Integer.parseInt(prop.getProperty("implicitlyWait"));
            int pageLoadTimeout = Integer.parseInt(prop.getProperty("pageLoadTimeout"));
            driverFactory = new DriverFactory();
            driver = driverFactory.init_driver(browserName);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        } catch (Exception e) {
            System.out.println("Please enter a number");
        }
    }

    @After(order = 0)
    public void quitBrowser() {
        driver.quit();
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);
        }
    }

}
