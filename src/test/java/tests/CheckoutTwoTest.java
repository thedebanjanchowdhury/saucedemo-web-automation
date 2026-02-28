package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class CheckoutTwoTest extends BaseTest {
    LoginPage loginPage;
    String FIRST_PRODUCT_NAME = "Sauce Labs Backpack";
    String SECOND_PRODUCT_NAME = "Sauce Labs Bike Light";
    String CHECKOUT_ONE_PAGE_URL = "https://www.saucedemo.com/checkout-step-one.html";
    String CHECKOUT_TWO_PAGE_URL = "https://www.saucedemo.com/checkout-step-two.html";
    double taxRate = 0.08;

//    @BeforeClass
//    public void beforeClass() {
//
//    }

    @BeforeMethod(onlyForGroups = {"requiresLogin"})
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCartByName(FIRST_PRODUCT_NAME);
        inventoryPage.addToCartByName(SECOND_PRODUCT_NAME);
        CartPage cartPage = inventoryPage.openCart();
        CheckoutOnePage checkoutOnePage = cartPage.openCheckoutFlow();
        checkoutOnePage.submitForm("Debanjan", "Chowdhury", "700001");
    }

    @Test(groups = {"requiresLogin", "smokeTests", "regressionTests"})
    @Description("Verify page load")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Debanjan Chowdhury")
    public void verifyPageLoad() {
        CheckoutTwoPage checkoutTwoPage = new CheckoutTwoPage(driver);
        Assert.assertTrue(checkoutTwoPage.isPageLoaded(),
                "Page not loaded");
    }

    @Test(groups = {"requiresLogin", "regressionTests"})
    @Description("Verify correct products in cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Debanjan Chowdhury")
    public void verifyCorrectProductsInCart() {
        CheckoutTwoPage checkoutTwoPage = new CheckoutTwoPage(driver);
        List<String> productNames = checkoutTwoPage.getProductNames();
        Assert.assertTrue(productNames.contains(FIRST_PRODUCT_NAME) && productNames.contains(SECOND_PRODUCT_NAME),
                "Mismatch in product names");
    }

    @Test(groups = {"requiresLogin", "regressionTests"})
    @Description("Verify correct tax amount calculation")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Debanjan Chowdhury")
    public void verifyCorrectTaxAmountCalculations() {
        CheckoutTwoPage checkoutTwoPage = new CheckoutTwoPage(driver);
        List<Double> productPrices = checkoutTwoPage.getProductPrices();
        double totalPrice = productPrices.stream().mapToDouble(Double::doubleValue).sum();
        DoubleUnaryOperator calculateTax = total -> total * taxRate;
        double calculatedTax = calculateTax.applyAsDouble(totalPrice);

        double roundedTax = Math.ceil(calculatedTax * 100.0) / 100.0;
        Assert.assertEquals(checkoutTwoPage.getTaxPrice(), roundedTax, "Tax price mismatch");
    }

    @Test(groups = {"requiresLogin", "regressionTests"})
    @Description("Verify correct cart subtotal calculation")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Debanjan Chowdhury")
    public void verifyCartSubtotalCalculations() {
        CheckoutTwoPage checkoutTwoPage = new CheckoutTwoPage(driver);
        List<Double> productPrice = checkoutTwoPage.getProductPrices();
        double totalPrice = productPrice.stream().mapToDouble(Double::doubleValue).sum();
        Assert.assertEquals(checkoutTwoPage.getSubtotalPrice(), totalPrice, "Subtotal price mismatch");
    }

    @Test(groups = {"requiresLogin", "regressionTests"})
    @Description("Verify correct cart total calculation")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Debanjan Chowdhury")
    public void verifyCartTotalCalculations() {
        CheckoutTwoPage checkoutTwoPage = new CheckoutTwoPage(driver);
        List<Double> productPrice = checkoutTwoPage.getProductPrices();
        double subtotalPrice = productPrice.stream().mapToDouble(Double::doubleValue).sum();
        DoubleUnaryOperator calculateTax = total -> total * taxRate;
        double calculatedTax = calculateTax.applyAsDouble(subtotalPrice);
        calculatedTax = Math.ceil(calculatedTax * 10) / 10;

        double totalAmount = subtotalPrice + calculatedTax;
        Assert.assertEquals(totalAmount, checkoutTwoPage.getTotalPrice(), "Total amount mismatch");
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify prevention of direct url access")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Debanjan Chowdhury")
    public void verifyDirectUrlAccess() {
        driver.get(CHECKOUT_TWO_PAGE_URL);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Current URL mismatch");
    }
}
