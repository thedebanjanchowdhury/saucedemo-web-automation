package tests;

import base.BaseTest;
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

    @BeforeClass
    public void loginSetup() {
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test(description = "Verify page loads successfully")
    public void verifyPageLoaded() {
        InventoryPage inventory = new InventoryPage(driver);
        Assert.assertTrue(inventory.isPageLoaded());
    }

    @Test(description = "Verify ascending sort of product names")
    public void verifyAscendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("az");

        List<String> actialList = inventory.getProductNames();
        List<String> sortedList = new ArrayList<>(actialList);
        Collections.sort(sortedList);

        Assert.assertEquals(sortedList, actialList, "Mismatch in ascending sort function");
    }

    @Test(description = "Verify descending sort of product names")
    public void verifyDescendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("za");

        List<String> actialList = inventory.getProductNames();
        List<String> sortedList = new ArrayList<>(actialList);
        Collections.sort(sortedList);
        Collections.reverse(sortedList);

        Assert.assertEquals(sortedList, actialList, "Mismatch in descending sort function");
    }

    @Test(description = "Verify ascending sort of product price")
    public void verifyPriceAscendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("lohi");
        List<Double> actialList = inventory.getProductPrices();
        List<Double> sortedList = new ArrayList<>(actialList);
        Collections.sort(sortedList);
        Assert.assertEquals(sortedList, actialList, "Mismatch in ascending price sort function");
    }

    @Test(description = "Verify descending sort of product price")
    public void verifyPriceDescendingSort() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.selectSortOption("hilo");
        List<Double> actualList = inventory.getProductPrices();
        List<Double> sortedList = new ArrayList<>(actualList);
        Collections.sort(sortedList);
        Collections.reverse(sortedList);
        Assert.assertEquals(actualList, sortedList, "Mismatch in descending price sort function");
    }

    @Test(description = "Verify product add to cart")
    public void verifyProductAddToCart() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addToCartByName("Sauce Labs Backpack");
        inventory.addToCartByName("Sauce Labs Bike Light");
        Assert.assertTrue(inventory.getAddToCartBadge().isDisplayed(), "Cart badge not displayed");
        Assert.assertEquals(inventory.getCartItemCount(), 2, "Mismatch in product count");
    }

    @Test(description = "Verify product navigation works properly")
    public void verifyProductNavigation() {
        InventoryPage inventory = new InventoryPage(driver);
        inventory.navigateToProduct("Sauce Labs Backpack");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"), "Mismatch in product URL");
    }
}
