package pages;

import util.DriverFactory;
import util.ElementUtil;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class HomePage {

    private final By myAccountTab = By.id("myAccount");
    private final By loginBtt = By.id("login");
    private final By emailTxtBox = By.id("txtUserName");
    private final By passwordTxtBox = By.id("txtPassword");
    private final By searchBox = By.cssSelector(".SearchBoxOld-inputContainer .desktopOldAutosuggestTheme-input");
    private final By searchResultTxt = By.cssSelector(".searchResultSummaryBar-mainText h1");
    private final By productList = By.cssSelector(".productListContent-item");
    private final By productName = By.id("product-name");
    private final By addToCart = By.id("addToCart");
    private final By toastMsgGoToBasket = By.cssSelector(".hb-toast-link");
    private final By goToBasketBtt = By.cssSelector(".checkoutui-SalesFrontCash-m5Re7 button");
    private final By shopCompleteBtt = By.cssSelector(".cta_3vBFp button");
    private final By shopNextBtt = By.cssSelector(".sc-AxjAm.cxvjoB.button_next_step_Dj-RE");
    private final By optionItem = By.cssSelector(".sardesPaymentPage-Accordion-accordion_tab p");
    private final By accordionItem = By.cssSelector(".sardesPaymentPage-Accordion-accordion_tab h3");
    private final By continueBtt = By.id("continue_step_btn");
    private final By paymentInfoDetails = By.cssSelector(".detail_20j8y");
    private final By deleteBasket = By.cssSelector(".delete_all_2uTds");
    private final By deletePopupApprove = By.cssSelector(".red_3m-Kl");


    private WebDriver driver;
    private ElementUtil elementUtil;
    private WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
    public String expectedProductText;
    public String productNameText;
    public String checkBankName;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.elementUtil = new ElementUtil(driver);
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    public void clickToLoginButton() {
        elementUtil.moveToElement(myAccountTab);
        elementUtil.click(loginBtt);
    }

    public void enterTheEmailAndEnter(String email) {
        elementUtil.sendKeyandEnter(emailTxtBox, email, Keys.ENTER);
    }

    public void enterThePasswordAndEnter(String password) {
        elementUtil.sendKeyandEnter(passwordTxtBox, password, Keys.ENTER);
    }

    public void checkHomePage(String title) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountTab));
        Assert.assertEquals(title, driver.getTitle());
    }

    public void typeIntoTheSearchBoxAndEnter(String product) {
        elementUtil.sendKeyandEnter(searchBox, product, Keys.ENTER);
        expectedProductText = product;
    }

    public void searchIsVerified() {
        Assert.assertEquals(expectedProductText, elementUtil.elementGetText(searchResultTxt));
    }

    public void clickOnProduct(int productNumber) {
        int sayac = 0;
        boolean check = false;
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productList));
        for (WebElement element : elements) {
            if (sayac == productNumber - 1) {
                productNameText = element.findElement(By.xpath("./div//h3")).getText();
                elementUtil.click(element);
                check = true;
                break;
            }
            sayac++;
        }
        Assert.assertTrue(check);
    }

    public void checkProductPage() {
        elementUtil.windowSwitchTo(1);
        Assert.assertEquals(productNameText, elementUtil.elementGetText(productName));
    }

    public void addToCart() {
        elementUtil.click(addToCart);
    }

    public void goToBasket() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(toastMsgGoToBasket));
            driver.findElement(toastMsgGoToBasket).click();
        } catch (Exception e) {
            boolean check = false;
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(goToBasketBtt));
            for (WebElement element : elements) {
                if (element.getText().equals("Sepete git")) {
                    elementUtil.click(element);
                    check = true;
                    break;
                }
            }
            Assert.assertTrue(check);
        }
    }

    public void clickOnCompletePurchase() {
        elementUtil.click(shopCompleteBtt);
        elementUtil.click(shopNextBtt);
    }

    public void clickOnPaymentMethod(String paymentMethod){
        boolean check = false;
        List<WebElement> tempelements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionItem));
        for (WebElement tempelement : tempelements) {
            wait.until(ExpectedConditions.stalenessOf(tempelement));
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionItem));
            for (WebElement element : elements) {
                if (element.getText().equals(paymentMethod)) {
                    elementUtil.click(element);
                    check = true;
                    break;
                }
            }
            if (check) {
                break;
            }
        }
        Assert.assertTrue(check);
    }

    public void clickOnBankOption(String bank){
        boolean check = false;
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionItem));
        for (WebElement element : elements) {
            if (element.getText().equals(bank)) {
                checkBankName = bank;
                elementUtil.click(element);
                check = true;
                break;
            }
        }
        Assert.assertTrue(check);
        elementUtil.click(continueBtt);
    }

    public void checkBankInfo() {
        boolean check = false;
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(paymentInfoDetails));
        for (WebElement element : elements) {
            if (element.getText().contains(checkBankName)) {
                check = true;
                break;
            }
        }
        Assert.assertTrue(check);
    }

    public void clearBasket(String url) {
        driver.get(url);
        elementUtil.click(deleteBasket);
        elementUtil.click(deletePopupApprove);
    }

}


