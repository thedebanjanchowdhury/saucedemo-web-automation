package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckoutOnePage {

    private final WebDriver driver;

    // Page Elements
    private static final By pageTitle = By.className("title");
    private static final By formContainer = By.className("checkout_info");
    private static final By firstNameInput = By.xpath("//input[@id='first-name']");
    private static final By lastNameInput = By.xpath("//input[@id='last-name']");
    private static final By zipInput = By.xpath("//input[@id='postal-code']");
    private static final By errorMessage = By.xpath("//h3[@data-test='error']");
    private static final By errorCloseBtn = By.xpath("//button[@class='error-button']");

    private static final By cancelBtn = By.xpath("//button[@id='cancel']");
    private static final By continueBtn = By.xpath("//input[@id='continue']");


    public  CheckoutOnePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPageLoaded() {
        return driver.findElement(pageTitle).isDisplayed()
                && driver.findElement(formContainer).isDisplayed();
    }

    public void submitForm(String firstName, String lastName, String zip) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(zipInput).sendKeys(zip);
        driver.findElement(continueBtn).click();
    }

    public void proceedCheckout() {
        driver.findElement(continueBtn).click();
    }


    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public void closeErrorDialog() {
        driver.findElement(errorCloseBtn).click();
    }

    public boolean isErrorDialogClosed() {
        return driver.findElements(errorMessage).isEmpty();
    }

    public void cancelCheckout() {
        driver.findElement(cancelBtn).click();
    }

    public void continueCheckout() {
        driver.findElement(continueBtn).click();
    }

}
