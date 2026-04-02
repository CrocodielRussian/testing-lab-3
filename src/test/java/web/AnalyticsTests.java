package web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    private WebDriverWait loginWait;

    private void setUpBrowser(String browser) {
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();

            // Подключаемся к браузеру, который мы заранее открыли через Терминал
            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9999");

            driver = new ChromeDriver(options);

        }
        else if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();

            options.addArguments("-profile");
            options.addArguments("C:\\selenium-firefox-profile");

            driver = new FirefoxDriver(options);
        }


        // Стандартное ожидание для элементов страницы (15 сек)
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Специальное долгое ожидание для ручной авторизации (90 сек)
        loginWait = new WebDriverWait(driver, Duration.ofSeconds(90));
    }

    @AfterEach
    void tearDown() {
        // Намеренно оставляем пустым.
        // Если сделать driver.quit(), браузер закроется, и следующий запуск упадет.
    }

    @BeforeEach
    void setUp() {
        // 1. Инициализация браузера
        setUpBrowser("chrome");
    }


    @Test
    @DisplayName("Функциональный тест интерфейса Google Analytics")
    void testAnalyticsInterface() {
        // 2. Открытие сайта
        driver.get("https://analytics.google.com/");

        System.out.println("Ожидание загрузки...");

        // 3. Ожидание загрузки сайта
        By dashboardLocator = By.xpath("//div[contains(@class, 'main-layout')]");
        WebElement dashboard = loginWait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLocator));

        System.out.println("Страница загружена! Запускаю авто-клики...");

        // 4. Переход в раздел "Отчеты"
        System.out.println("Кликаю на 'Отчеты'...");
        By reportsMenuLocator = By.xpath("//a[contains(@guidedhelpid, 'guided-help-reports-module')]");
        WebElement reportsButton = wait.until(ExpectedConditions.elementToBeClickable(reportsMenuLocator));
        reportsButton.click();

        // Проверка: загрузился ли заголовок отчетов
        By reportHeader = By.xpath("//h1 | //*[contains(text(), 'Сводка') or contains(text(), 'Reports snapshot')]");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(reportHeader));
        assertTrue(header.isDisplayed(), "Раздел отчетов не открылся!");

        // 5. Переход в раздел "Администратор"
        System.out.println("Кликаю на 'Администратор'...");
        By adminButtonLocator = By.xpath("//a[contains(@guidedhelpid, 'guided-help-admin-module')]");
        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(adminButtonLocator));
        adminButton.click();

        // Проверка: загрузилась ли панель настроек
        By adminPanelLocator = By.xpath("//h1[contains(text(), 'Администратор')]");
        WebElement adminPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(adminPanelLocator));
        assertTrue(adminPanel.isDisplayed(), "Панель администратора не загрузилась!");

        System.out.println("Тест успешно пройден!");
    }

    @Test
    @DisplayName("Функциональный тест раздела 'Отчёты'")
    void testReportsModule() {
        // 2. Открытие сайта
        driver.get("https://analytics.google.com/");

        System.out.println("Ожидание загрузки...");

        // 3. Ожидание загрузки сайта
        By dashboardLocator = By.xpath("//div[contains(@class, 'main-layout')]");
        WebElement dashboard = loginWait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLocator));

        System.out.println("Страница загружена! Запускаю авто-клики...");

        // 4. Переход в раздел "Отчеты"
        System.out.println("Кликаю на 'Отчеты'...");
        By reportsMenuLocator = By.xpath("//a[contains(@guidedhelpid, 'guided-help-reports-module')]");
        WebElement reportsButton = wait.until(ExpectedConditions.elementToBeClickable(reportsMenuLocator));
        reportsButton.click();

        // Проверка: загрузился ли заголовок отчетов
        By reportHeader = By.xpath("//h1 | //*[contains(text(), 'Сводка') or contains(text(), 'Reports snapshot')]");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(reportHeader));
        assertTrue(header.isDisplayed(), "Раздел отчетов не открылся!");

        System.out.println("Кликаю на 'Обзор в режиме реального времени'...");
        By menuOfReports = By.xpath("//span[contains(@class, 'mdc-list-item__content')]");
        WebElement realtimeOverviewButton = wait.until(ExpectedConditions.elementToBeClickable(menuOfReports));
        realtimeOverviewButton.click();

        By widgetTab = By.xpath("//*[contains(@class, 'xap-card-content card-content')]");
        WebElement realtimeTab = wait.until(ExpectedConditions.visibilityOfElementLocated(widgetTab));
        assertTrue(realtimeTab.isDisplayed(), "Виджет реального времени не открылся!");


        System.out.println("Кликаю на 'Добавить сравнения'...");
        By menuOfActions = By.xpath("//button[contains(@class, 'report-filter add-comparison')]");
        WebElement addCompareButton = wait.until(ExpectedConditions.elementToBeClickable(menuOfActions));
        addCompareButton.click();


        System.out.println("Тест успешно пройден!");
    }
}