package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.time.Duration;

public class CartTests extends BaseTest {
    LoginPage loginPage;

    @BeforeClass
    public void beforeClass(){

    }

    @BeforeMethod
    public void beforeMethod(){
        loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory"));
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify successful page load")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySuccessfulPageLoad(){
        InventoryPage inventory = new InventoryPage(driver);
        inventory.resetAppState();
        inventory.addToCartByName("Sauce Labs Backpack");
        CartPage cartPage = inventory.openCart();
        Assert.assertTrue(cartPage.isPageLoaded(), "Cart page is not loaded");
    }

    @Test(groups = {"regressionTests"}, dataProvider = "productNames")
    @Description("Verify add multiple items")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddMultipleItems(String productName) {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.resetAppState();
        inventory.addToCartByName(productName);
        CartPage cartPage = inventory.openCart();
        Assert.assertTrue(cartPage.isProductPresent(productName),"Product not found: " +productName);
    }


    @Test(groups = {"regressionTests"})
    @Description("Verify remove items")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyRemoveItem() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.resetAppState();
        inventory.addToCartByName("Sauce Labs Backpack");
        CartPage cartPage = inventory.openCart();
        cartPage.removeProduct("Sauce Labs Backpack");
        Assert.assertFalse(cartPage.isProductPresent("Sauce Labs Backpack"), "Product found: " + "Sauce Labs Backpack");
    }

    @Test(groups = {"regressionTests"}, dataProvider = "productNames")
    @Description("Verify remove multiple items")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyRemoveItems(String productName) {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.resetAppState();
        inventory.addToCartByName(productName);
        CartPage cartPage = inventory.openCart();
        cartPage.removeProduct(productName);
        Assert.assertFalse(cartPage.isProductPresent(productName), "Product found: " + productName);
    }

    @Test(groups = {"regressionTests", "smokeTests"})
    @Description("Verify checkout navigation")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCheckoutNavigation() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.resetAppState();
        inventory.addToCartByName("Sauce Labs Backpack");
        CartPage cartPage = inventory.openCart();
        cartPage.openCheckoutFlow();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-one.html",
        "Malfunction in checkout navigation");
    }

    @DataProvider(name = "productNames")
    public static Object[][] productNames() {
        return new Object[][] {
                {"Sauce Labs Backpack"},
                {"Sauce Labs Bike Light"},
        };
    }
}
