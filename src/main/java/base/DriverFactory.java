    package base;

    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.openqa.selenium.chrome.ChromeOptions;
    import org.openqa.selenium.edge.EdgeDriver;
    import org.openqa.selenium.edge.EdgeOptions;
    import org.openqa.selenium.firefox.FirefoxDriver;
    import org.openqa.selenium.firefox.FirefoxOptions;

    public class DriverFactory {
        private static final ThreadLocal<WebDriver> driver = new  ThreadLocal<>();

        public static WebDriver getDriver(String browser, boolean headless) {
            if (driver.get() == null) {
                switch (browser.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (headless) {
                            chromeOptions.addArguments("--headless=new");
                        }
                        driver.set(new ChromeDriver(chromeOptions));
                        break;
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (headless) {
                            firefoxOptions.addArguments("-headless");
                        }
                        driver.set(new FirefoxDriver(firefoxOptions));
                        break;
                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        if (headless) {
                            edgeOptions.addArguments("--headless=new");
                        }
                        driver.set(new EdgeDriver(edgeOptions));
                        break;
                    default:
                        throw new RuntimeException("Browser not supported: " + browser);
                }
            }
            return driver.get();
        }

        public static void quitDriver() {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
            }
        }
    }
