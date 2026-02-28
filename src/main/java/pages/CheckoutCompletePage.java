package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutCompletePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By pageTitle = By.className("title");
    private static final By orderMessage = By.className("complete-header");
    private static final By backToHome = By.id("back-to-products");

    private static final String URL = "https://www.saucedemo.com/checkout-complete.html";

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        return driver.getCurrentUrl().contains("checkout-complete")
                && driver.findElement(pageTitle).isDisplayed()
                && driver.findElement(orderMessage).isDisplayed()
                && driver.findElement(backToHome).isDisplayed();
    }

    public String getOrderMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderMessage)).getText();
    }

    public void backToHome() {
        wait.until(ExpectedConditions.elementToBeClickable(backToHome)).click();
    }

    public String getUrl() {
        return URL;
    }
}
