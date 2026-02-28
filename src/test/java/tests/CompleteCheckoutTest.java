package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CompleteCheckoutTest extends BaseTest {

    LoginPage loginPage;
    String FIRST_PRODUCT_NAME = "Sauce Labs Backpack";
    String SECOND_PRODUCT_NAME = "Sauce Labs Bike Light";

//    @BeforeClass
//    public void beforeClass() {
//
//    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCartByName(FIRST_PRODUCT_NAME);
        inventoryPage.addToCartByName(SECOND_PRODUCT_NAME);
        CartPage cartPage = inventoryPage.openCart();
        CheckoutOnePage checkoutOnePage = cartPage.openCheckoutFlow();
        checkoutOnePage.submitForm("Debanjan", "Chowdhury", "700001");
        CheckoutTwoPage checkoutTwoPage = new CheckoutTwoPage(driver);
        checkoutTwoPage.proceedToCheckout();
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify page load")
    @Severity(SeverityLevel.BLOCKER)
    public void verifyPageLoad() {
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);
        Assert.assertTrue(checkoutCompletePage.isPageLoaded(),
                "Checkout page is not loaded");
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify homepage redirection")
    @Severity(SeverityLevel.NORMAL)
    public void verifyHomePageRedirection() {
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);
        checkoutCompletePage.backToHome();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Mismatch in redirection URL");
    }

}
