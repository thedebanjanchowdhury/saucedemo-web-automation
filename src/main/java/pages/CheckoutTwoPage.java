package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutTwoPage {
    private final WebDriver driver;

    // Product Details
    private static final By cartList = By.className("cart-list");
    private static final By cartItem = By.className("cart-item");
    private static final By productName = By.className("inventory_item_name");
    private static final By productDesc = By.className("inventory_item_desc");
    private static final By productPrice = By.className("inventory_item_price");

    // Summary information
    private static final By summaryContainer = By.className("summary_info");
    private static final By subtotalPrice = By.className("summary_subtotal_label");
    private static final By taxPrice = By.className("summary_tax_label");
    private static final By totalPrice = By.className("summary_total_label");




    public CheckoutTwoPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPageLoaded() {
        return driver.findElement(summaryContainer).isDisplayed();
    }

    public List<String> getProductNames() {
        return driver.findElements(productName)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return driver.findElements(productPrice)
                .stream()
                .map(e -> e.getText().replace("$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    public Double getTaxPrice() {
        return Double.parseDouble(driver.findElement(taxPrice).getText().replace("Tax: $", ""));
    }

    public Double getSubtotalPrice() {
        return Double.parseDouble(
          driver.findElement(subtotalPrice).getText().replace("Item total: $", "")
        );
    }

    public Double getTotalPrice() {
        return Double.parseDouble(
                driver.findElement(totalPrice).getText().replace("Total: $", "")
        );
    }

    public String getLabelPrice() {
        return driver.findElement(productPrice).getText();
    }
}
