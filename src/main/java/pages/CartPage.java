package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    public final WebDriver driver;

    // Page Elements
    private static final By pageTitle = By.className("title");
    private static final By cartProduct = By.className("cart_item");
    private static final By productQty = By.className("cart_quantity");
    private static final By productName = By.className("inventory_item_name");
    private static final By productDesc = By.className("inventory_item_desc");
    private static final By productPrice = By.className("inventory_item_price");
    private static final By productRemoveBtn = By.xpath(".//button[contains(text(), 'Remove')]");

    // Footer
    private static final By continueShoppingBtn = By.id("continue-shopping");
    private static final By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPageToLoad(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutBtn));
    }

    public boolean isPageLoaded() {
        return driver.findElement(pageTitle).isDisplayed()
                && driver.findElement(continueShoppingBtn).isDisplayed();
    }

    public boolean isProductPresent(String productName) {
       return getProductTitles().contains(productName);
    }

    public boolean isProductDisplayed() {
        List<WebElement> products = driver.findElements(
                By.xpath("//div[@class='inventory_item_name' and text()='"+productName+"']")
        );

        return !products.isEmpty();
    }

    public void removeProduct(String name) {
        List<WebElement> products = driver.findElements(cartProduct);
        for (WebElement product: products) {
            String itemName = product.findElement(productName).getText();
            if (itemName.equals(name)) {
                product.findElement(productRemoveBtn).click();
                return;
            }
        }
    }

    public CheckoutOnePage openCheckoutFlow() {
        driver.findElement(checkoutBtn).click();
        return new CheckoutOnePage(driver);
    }

    // Utility Function
    public List<String> getProductTitles() {
        return driver.findElements(productName)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
