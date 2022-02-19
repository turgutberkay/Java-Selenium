package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Reporter;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public WebDriver driver;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    public WebDriver init_driver(String browser) throws MalformedURLException {
        //FOR JUST RUN
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            tlDriver.set(new ChromeDriver(options));
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());
        } else if (browser.equals("safari")) {
            tlDriver.set(new SafariDriver());
        } else if (browser.equals("opera")) {
            tlDriver.set(new OperaDriver());
        }
        //FOR HEADLESS RUN
        else if (browser.equals("chrome-headless")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("disable-gpu");
            tlDriver.set(new ChromeDriver(options));
        }
        //FOR SELENİUM GRİD RUN
        String port = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("port");
        if (browser.equals("chrome-grid")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            tlDriver.set(new RemoteWebDriver(new URL(port), options));
        } else if (browser.equals("firefox-grid")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("start-maximized");
            tlDriver.set(new RemoteWebDriver(new URL(port), options));
        }else if (browser.equals("edge-grid")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("start-maximized");
            tlDriver.set(new RemoteWebDriver(new URL(port), options));
        } else {
            System.out.println("Please pass the correct browser value: " + browser);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
}
