package web.base;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait loginWait;

    @BeforeAll
    void setUpAll() {
        setUpBrowser("chrome");
    }

    @BeforeEach
    void setUp() {
        driver.get("https://analytics.google.com/");
        By dashboardLocator = By.xpath("//div[contains(@class, 'main-layout')]");
        loginWait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLocator));
    }

    @AfterEach
    void tearDown() {
        // при необходимости: скриншот при падении и т.д.
    }

    private void setUpBrowser(String browser) {
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9999");
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("privacy.trackingprotection.enabled", false);
            options.addPreference("browser.contentblocking.category", "standard");
            try {
                driver = new org.openqa.selenium.remote.RemoteWebDriver(
                        new java.net.URL("http://localhost:4444"), options
                );
            } catch (java.net.MalformedURLException e) {
                throw new RuntimeException("Неверный URL: " + e.getMessage());
            }
        }

        wait      = new WebDriverWait(driver, Duration.ofSeconds(15));
        loginWait = new WebDriverWait(driver, Duration.ofSeconds(90));
    }
}