package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.time.Duration;
import java.util.logging.LogManager;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setup() {
        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("url");
        long implicitWaitTime = Long.parseLong(ConfigReader.getProperty("implicit.wait"));

        driver = DriverFactory.getDriver(browser, true);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
    }

    @AfterMethod
    public void resetApp() {
        String url = ConfigReader.getProperty("url");
        driver.manage().deleteAllCookies();
        driver.get(url);
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
