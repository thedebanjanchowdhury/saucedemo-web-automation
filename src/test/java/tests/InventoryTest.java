package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTest extends BaseTest {

    LoginPage loginPage;

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test(groups = {"smokeTests", "regressionTests"})
    @Description("Verify page loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPageLoaded() {
        InventoryPage inventory = new InventoryPage(driver);
        Assert.assertTrue(inventory.isPageLoaded());
    }


    @Test(groups = {"regressionTests"})
    @Description("Verify ascending sort of product names")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAscendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("az");

        List<String> actialList = inventory.getProductNames();
        List<String> sortedList = new ArrayList<>(actialList);
        Collections.sort(sortedList);

        Assert.assertEquals(sortedList, actialList, "Mismatch in ascending sort function");
    }

    @Test(groups = {"regressionTests"})
    @Description("Verify descending sort of product names")
    @Severity(SeverityLevel.NORMAL)
    public void verifyDescendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("za");

        List<String> actialList = inventory.getProductNames();
        List<String> sortedList = new ArrayList<>(actialList);
        Collections.sort(sortedList);
        Collections.reverse(sortedList);

        Assert.assertEquals(sortedList, actialList, "Mismatch in descending sort function");
    }

    @Test(groups = {"regressionTests"})
    @Description("Verify ascending sort of product price")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPriceAscendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("lohi");
        List<Double> actialList = inventory.getProductPrices();
        List<Double> sortedList = new ArrayList<>(actialList);
        Collections.sort(sortedList);
        Assert.assertEquals(sortedList, actialList, "Mismatch in ascending price sort function");
    }

    @Test(groups = {"regressionTests"})
    @Description("Verify descending sort of product price")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPriceDescendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("hilo");
        List<Double> actualList = inventory.getProductPrices();
        List<Double> sortedList = new ArrayList<>(actualList);
        Collections.sort(sortedList);
        Collections.reverse(sortedList);
        Assert.assertEquals(actualList, sortedList, "Mismatch in descending price sort function");
    }

    @Test(groups = {"regressionTests"})
    @Description("Verify product add to cart")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProductAddToCart() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addToCartByName("Sauce Labs Backpack");
        inventory.addToCartByName("Sauce Labs Bike Light");
        Assert.assertTrue(inventory.getAddToCartBadge().isDisplayed(), "Cart badge not displayed");
        Assert.assertEquals(inventory.getCartItemCount(), 2, "Mismatch in product count");
    }

    @Test(groups = {"regressionTests", "smokeTests"})
    @Description("Verify product navigation works properly")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProductNavigation() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.navigateToProduct("Sauce Labs Backpack");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"), "Mismatch in product URL");
    }
}
