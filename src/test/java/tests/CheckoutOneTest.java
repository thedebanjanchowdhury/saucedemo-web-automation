package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CheckoutOnePage;
import pages.InventoryPage;
import pages.LoginPage;

public class CheckoutOneTest extends BaseTest {
    LoginPage loginPage;

//    @BeforeClass
//    public void beforeClass() {
//
//    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addToCartByName("Sauce Labs Backpack");
        driver.navigate().to("https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify page loaded successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPageLoad() {
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage(driver);
        Assert.assertTrue(checkoutOnePage.isPageLoaded(), "Checkout page is not loaded");
    }

    @Test(dataProvider = "formDataProvider", groups = {"regressionTests"})
    @Description("Verify form check validation")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyFormValidation(String firstName, String lastName, String zip, String type) {
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage(driver);
        checkoutOnePage.submitForm(firstName, lastName, zip);

        switch (type) {
            case "happy":
                Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
                        "Malfunction in redirection");
                break;
            case "firstName":
                Assert.assertTrue(checkoutOnePage.getErrorMessage().contains("First Name"),
                        "Mismatch in error message");
                break;
            case "lastName":
                Assert.assertTrue(checkoutOnePage.getErrorMessage().contains("Last Name"),
                        "Mismatch in error message");
                break;
            case "zip":
                Assert.assertTrue(checkoutOnePage.getErrorMessage().contains("Postal Code"),
                        "Mismatch in error message");
        }
    }

    @Test(groups = {"regressionTests"})
    @Description("Verify error dialog close")
    @Severity(SeverityLevel.NORMAL)
    public void verifyErrorDialogClose() {
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage(driver);
        checkoutOnePage.submitForm("firstName", "lastName", "");
        checkoutOnePage.closeErrorDialog();
        Assert.assertTrue(checkoutOnePage.isErrorDialogClosed(), "Error dialog not closed");
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify cancel checkout navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCancelCheckoutNavigation() {
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage(driver);
        checkoutOnePage.cancelCheckout();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Malfunction in cancel redirection");
    }

    @Test(groups = {"regressionTests", "smokeTests"})
    @Description("Verify proceed checkout navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProceedCheckoutNavigation() {
        CheckoutOnePage checkoutOnePage = new CheckoutOnePage(driver);
        checkoutOnePage.submitForm("firstName", "lastName", "zip");
        System.out.println(driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html",
                "Malfunction in proceed redirection");
    }

    @DataProvider(name = "formDataProvider")
    public Object[][] formDataProvider() {
        return new Object[][]{
                {"John", "Doe", "700001", "happy"},
                {"", "Doe", "700001", "firstName"},
                {"John", "", "700001", "lastName"},
                {"John", "Doe", "", "zip"},
                {"", "", "", "firstName"}
        };
    }
}
