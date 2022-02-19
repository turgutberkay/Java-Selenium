package util;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;


public class ElementUtil {

    private WebDriver driver;
    private WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
    private Actions action;


    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
    }

    public void click(By byElement) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void click(WebElement webelement) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webelement));
            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void sendKeyandEnter(By byElement, String string, Keys key) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);
            element.sendKeys(string);
            element.sendKeys(key);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public void moveToElement(By by) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            action.moveToElement(element).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public String elementGetText(By by) {
        String a = null;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            a = driver.findElement(by).getText();

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        return a;
    }

    public void windowSwitchTo(int i) {
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(i));
    }

}