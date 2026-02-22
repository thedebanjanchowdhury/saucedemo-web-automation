package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private final WebDriver driver;

    // Page Elements
    private static final By pageTitle = By.className("title");
    private static final By orderByBtn = By.xpath("//select[@class='product_sort_container']");
    private static final By inventoryList = By.className("inventory_list");
    private static final By productCard = By.className("inventory_item");
    private static final By shoppingCartBadge = By.className("shopping_cart_badge");
    private static final By cartPageBtn = By.xpath("//a[@class='shopping_cart_link']");

    // Hamburger Elements
    private static final By logoutLink = By.id("logout_sidebar_link");
    private static final By hamburgerMenu = By.id("react-burger-menu-btn");

    // Product Card Elements
    private static final By productImg = By.className("inventory_item_img");
    private static final By productName = By.className("inventory_item_name");
    private static final By productDescription = By.className("inventory_item_desc");
    private static final By productPrice = By.className("inventory_item_price");
//    private static final By addToCartBtn = By.xpath(".//button[contains(text(), 'Add to cart')]");
    private static final By addToCartBtn = By.xpath(".//div[@class='pricebar']//button");


    public InventoryPage(WebDriver driver) {
        this.driver  = driver;
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
    }

    public boolean isPageLoaded() {
        return driver.getCurrentUrl().contains("inventory.html")
                && driver.findElement(pageTitle).isDisplayed()
                && driver.findElement(orderByBtn).isDisplayed()
                && driver.findElement(inventoryList).isDisplayed();
    }

    public boolean isProductCountCorrect() {
        return driver.findElements(productCard).size() == 6;
    }

    public void resetAppState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(hamburgerMenu).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("reset_sidebar_link")));
        driver.findElement(By.id("reset_sidebar_link")).click();
        driver.navigate().refresh();
    }

    public boolean isProductCardVisible() {
        return driver.findElement(productImg).isDisplayed()
                && driver.findElement(productName).isDisplayed()
                && driver.findElement(productDescription).isDisplayed()
                && driver.findElement(productPrice).isDisplayed()
                && driver.findElement(addToCartBtn).isDisplayed();
    }

    public void addToCart(WebElement card) {
        card.findElement(addToCartBtn).click();
    }

    public void addToCartByName(String name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCard));

        List<WebElement> items = driver.findElements(productCard);

        for (WebElement item : items) {
            String productTitle = item.findElement(productName).getText().trim();
            if (productTitle.equals(name)) {
                item.findElement(addToCartBtn).click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + name);
    }

    public int getCartItemCount() {
        return Integer.parseInt(driver.findElement(shoppingCartBadge).getText());
    }

    public void selectSortOption(String value) {
        Select select = new  Select(driver.findElement(orderByBtn));
        select.selectByValue(value);
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

    public List<WebElement> getAllProducts() {
        return driver.findElements(productCard);
    }

    public void navigateToProduct(String name) {
        List<WebElement> products = getAllProducts();
        for (WebElement product: products) {
            String productTitle = product.findElement(productName).getText();
            if (productTitle.equals(name)) {
                openProductPage(product);
                return;
            }
        }
    }

    public CartPage openCart() {
        driver.findElement(cartPageBtn).click();
        return  new CartPage(driver);
    }

    public void openProductPage(WebElement product) {
        product.findElement(productName).click();
    }

    public WebElement getAddToCartBadge() {
        return driver.findElement(shoppingCartBadge);
    }


}
