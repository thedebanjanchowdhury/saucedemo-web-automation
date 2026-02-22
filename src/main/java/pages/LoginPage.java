package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import utils.ConfigReader;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private static final By userNameField = By.id("user-name");
    private static final By passwordField = By.id("password");
    private static final By loginButton = By.id("login-button");
    private static final By errorMessage = By.className("error-message-container");
    private static final By closeErrorMessage = By.xpath("//button[@class='error-button']");

    private static final By hamburgerMenu = By.id("react-burger-menu-btn");
    private static final By logoutLink = By.id("logout_sidebar_link");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return ConfigReader.getProperty("url");
    }

    public void enterUserName(String userName){
        driver.findElement(userNameField).sendKeys(userName);
    }

    public void enterPassword(String password){
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton(){
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password){
        enterUserName(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(hamburgerMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
        driver.findElement(logoutLink).click();
    }

    public String errorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public void closeErrorMessage() {
        driver.findElement(closeErrorMessage).click();
    }

    public boolean isOnInventoryPage () {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    public boolean isErrorDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }
}
