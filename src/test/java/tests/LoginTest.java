package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "userCredentials")
    @Description("Verify User Login")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyLoginTest(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult.equals("success")) {
            Assert.assertTrue(loginPage.isOnInventoryPage(), "Malfunction in login redirection");
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error not displayed while incorrect login");
        }
    }

    @Test(dataProvider = "negativeTestCases")
    @Description("Verify negative login inputs")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNegativeTestCases(String username, String password, String condition) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");

        switch (condition) {
            case "emptyFields":
                Assert.assertTrue(loginPage.errorMessage().contains("required"),
                        "Mismatch in error message");
                break;
            case "emptyUsername":
                Assert.assertTrue(loginPage.errorMessage().contains("Username is required"),
                        "Incorrect error message");
                break;
            case "emptyPassword":
                Assert.assertTrue(loginPage.errorMessage().contains("Password is required"),
                        "Incorrect error message");
                break;
        }
    }

    @Test(dataProvider = "validationTestCases")
    @Description("Verify validation input edge cases")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyValidationTestCases(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");
    }

    @Test
    @Description("Verify error dialog close")
    @Severity(SeverityLevel.NORMAL)
    public void verifyErrorDialogClose() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "admin");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");

        loginPage.closeErrorMessage();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message displayed after close");
    }

    @Test
    @Description("Verify direct url access(first)")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDirectURLAccess() {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("https://saucedemo.com/inventory.html");

        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getUrl());
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button not displayed");
    }

    @Test
    @Description("Verfiy direct url access(second)")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDirectURLAccess2() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        loginPage.logout();
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button not displayed");

        driver.get("https://saucedemo.com/inventory.html");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button not displayed");
    }

    @DataProvider(name = "userCredentials")
    public static Object[][] getData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", "success"},
                {"locked_out_user", "secret_sauce", "failure"},
                {"problem_user", "secret_sauce", "success"},
                {"performance_glitch_user", "secret_sauce", "success"},
                {"error_user", "secret_sauce", "success"},
                {"visual_user", "secret_sauce", "success"},
        };
    }

    @DataProvider(name = "negativeTestCases")
    public static Object[][] getNegativeTestCases() {
        return new Object[][]{
                {"", "", "emptyFields"},
                {"", "sauce_demo", "emptyUsername"},
                {"standard_user", "", "emptyPassword"},
        };
    }

    @DataProvider(name = "validationTestCases")
    public static Object[][] getValidationTestCases() {
        return new Object[][]{
                {" standard_user", "secret_sauce"},
                {"standard_user ", "secret_sauce"},
                {"Standard_user", "secret_sauce"},
                {"@#%^&*", "secret_sauce"},
                {"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ExtraValidationTestStringForLengthCheck", "secret_sauce"}
        };
    }

}
