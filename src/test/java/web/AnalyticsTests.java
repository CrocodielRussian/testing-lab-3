package web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalyticsTests {

    private WebDriver driver;
    private WebDriverWait wait;

    // Инициализация браузера перед каждым тестом
    private void setUpBrowser(String browser) {
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            // ВАЖНО: Укажи путь к своему профилю Chrome, чтобы обойти логин Google
            // options.addArguments("user-data-dir=/Users/ivanmironov/Library/Application Support/Google/Chrome/");
            // options.addArguments("profile-directory=Default");
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            // Для Firefox тоже можно подключить профиль, если потребуется
            driver = new FirefoxDriver(options);
        }

        driver.manage().window().maximize();
        // Настраиваем явное ожидание (до 15 секунд), т.к. GA грузится долго
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер после теста
        }
    }

    // =========================================================================
    // СЦЕНАРИЙ 1: Проверка загрузки главного дашборда
    // =========================================================================
    @ParameterizedTest(name = "Test Dashboard Load in {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    @DisplayName("Проверка наличия главного контейнера аналитики")
    void testDashboardLoads(String browser) {
        setUpBrowser(browser);
        driver.get("https://analytics.google.com/");

        // Ожидаем появления элемента по XPath (ищем любой div, класс которого содержит 'root' или 'dashboard')
        // В GA классы динамические, поэтому используем функцию contains()
        By dashboardLocator = By.xpath("//div[contains(@class, 'page-content') or contains(@class, 'layout-container')]");

        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLocator));
        assertTrue(dashboard.isDisplayed(), "Главный дашборд не загрузился!");
    }

    // =========================================================================
    // СЦЕНАРИЙ 2: Переход в раздел "Отчеты" (Reports)
    // =========================================================================
    @ParameterizedTest(name = "Test Reports Menu in {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    @DisplayName("Навигация в раздел 'Отчеты' через левое меню")
    void testNavigateToReports(String browser) {
        setUpBrowser(browser);
        driver.get("https://analytics.google.com/");

        // XPath: Ищем ссылку (a) или кнопку (div), внутри которой есть текст "Отчеты" или "Reports"
        By reportsMenuLocator = By.xpath("//*[contains(text(), 'Отчеты') or contains(text(), 'Reports')]//ancestor::a | //*[contains(text(), 'Отчеты') or contains(text(), 'Reports')]//ancestor::button");

        WebElement reportsButton = wait.until(ExpectedConditions.elementToBeClickable(reportsMenuLocator));
        reportsButton.click();

        // Проверяем, что URL изменился или открылся заголовок отчетов
        By reportHeader = By.xpath("//h1[contains(text(), 'Сводка') or contains(text(), 'Reports snapshot')]");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(reportHeader));

        assertTrue(header.isDisplayed(), "Раздел отчетов не открылся!");
    }

    // =========================================================================
    // СЦЕНАРИЙ 3: Переход в раздел "Администратор" (Admin)
    // =========================================================================
    @ParameterizedTest(name = "Test Admin Panel in {0}")
    @ValueSource(strings = {"chrome", "firefox"})
    @DisplayName("Открытие панели администратора (иконка шестеренки)")
    void testNavigateToAdmin(String browser) {
        setUpBrowser(browser);
        driver.get("https://analytics.google.com/");

        // XPath: Иконка шестеренки обычно имеет aria-label='Администратор' или содержит специфичный svg
        By adminButtonLocator = By.xpath("//a[contains(@aria-label, 'Администратор') or contains(@aria-label, 'Admin')] | //button[contains(@aria-label, 'Администратор') or contains(@aria-label, 'Admin')]");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(adminButtonLocator));
        adminButton.click();

        // Проверяем наличие блока настроек аккаунта или ресурса
        By adminPanelLocator = By.xpath("//div[contains(text(), 'Настройки аккаунта') or contains(text(), 'Account settings')]");
        WebElement adminPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(adminPanelLocator));

        assertTrue(adminPanel.isDisplayed(), "Панель администратора не загрузилась!");
    }
}