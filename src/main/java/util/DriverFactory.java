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
        String port = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("port");
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
        //FOR SELENİUM GRİD RUN
        else if (browser.equals("chrome-grid")) {
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
        }
        //FOR DOCKERIZE RUN
        else if (browser.equals("chrome-dockerize")) {
            System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            System.setProperty("webdriver.chrome.args", "--disable-logging");
            System.setProperty("webdriver.chrome.silentOutput", "true");
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("window-size=1024,768"); // Bypass OS security model
            tlDriver.set(new ChromeDriver(options));
        }else {
            System.out.println("Please pass the correct browser value: " + browser);
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public static synchronized WebDriver getDriver() {return tlDriver.get();}
}
