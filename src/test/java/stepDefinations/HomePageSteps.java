package stepDefinations;

import pages.HomePage;
import util.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class HomePageSteps {

    WebDriver driver = DriverFactory.getDriver();
    public HomePage homePage = new HomePage(driver);

    @Given("Go to {string}")
    public void goToUrl(String url) {
        homePage.goToUrl(url);
    }

    @When("Login with {string} email and {string} password")
    public void loginWithEmailAndPassword(String email, String password) {
        homePage.clickToLoginButton();
        homePage.enterTheEmailAndEnter(email);
        homePage.enterThePasswordAndEnter(password);
        homePage.checkHomePage("Türkiye'nin En Büyük Online Alışveriş Sitesi Hepsiburada.com");
    }

    @When("Search {string} and add basket {int}. product")
    public void searchAndAddBasketProduct(String product, int productNumber) {
        homePage.typeIntoTheSearchBoxAndEnter(product);
        homePage.searchIsVerified();
        homePage.clickOnProduct(productNumber);
        homePage.checkProductPage();
        homePage.addToCart();
    }

    @When("^Go to order summary page with (.*) and (.*)$")
    public void goToOrderSummaryPageWithBankAndPaymentMethod(String bank, String paymentMethod){
        homePage.goToBasket();
        homePage.clickOnCompletePurchase();
        homePage.clickOnPaymentMethod(paymentMethod);
        homePage.clickOnBankOption(bank);
    }

    @Then("The bank name in the payment information field is checked")
    public void theBankNameInThePaymentInformationFieldIsChecked() {
        homePage.checkBankInfo();
    }

    @Then("Clear basket for next scenarios")
    public void clearBasketForNextScenarios() {
        homePage.clearBasket("https://checkout.hepsiburada.com/sepetim");
    }

}
