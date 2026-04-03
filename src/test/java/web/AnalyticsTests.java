package web;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        System.out.println("Нажимаю на 'Отчеты'...");
        By reportsMenuLocator = By.xpath("//a[contains(@guidedhelpid, 'guided-help-reports-module')]");
        WebElement reportsButton = wait.until(ExpectedConditions.elementToBeClickable(reportsMenuLocator));
        reportsButton.click();

        // Проверка: загрузился ли заголовок отчетов
        By reportHeader = By.xpath("//h1 | //*[contains(text(), 'Сводка') or contains(text(), 'Reports snapshot')]");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(reportHeader));
        assertTrue(header.isDisplayed(), "Раздел отчетов не открылся!");

        // 5. Переход в раздел "Администратор"
        System.out.println("Нажимаю на 'Администратор'...");
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
        System.out.println("Нажимаю на 'Отчеты'...");
        By reportsMenuLocator = By.xpath("//a[contains(@guidedhelpid, 'guided-help-reports-module')]");
        WebElement reportsButton = wait.until(ExpectedConditions.elementToBeClickable(reportsMenuLocator));
        reportsButton.click();

        // Проверка: загрузился ли заголовок отчетов
        By reportHeader = By.xpath("//h1 | //*[contains(text(), 'Сводка') or contains(text(), 'Reports snapshot')]");
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(reportHeader));
        assertTrue(header.isDisplayed(), "Раздел отчетов не открылся!");

        System.out.println("Нажимаю на 'Обзор в режиме реального времени'...");
        By menuOfReports = By.xpath("//button[contains(@guidedhelpid, 'guided-help-realtime-overview')]");
        WebElement realtimeOverviewButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(menuOfReports)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                realtimeOverviewButton
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                realtimeOverviewButton
        );

        By widgetTab = By.xpath("//*[contains(@class, 'xap-card-content card-content')]");
        WebElement realtimeTab = wait.until(ExpectedConditions.visibilityOfElementLocated(widgetTab));
        assertTrue(realtimeTab.isDisplayed(), "Виджет реального времени не открылся!");


        System.out.println("Нажимаю на 'Добавить сравнения'...");
        By menuOfActions = By.xpath("//button[contains(@class, 'report-filter add-comparison')]");
        WebElement addCompareButton = wait.until(ExpectedConditions.elementToBeClickable(menuOfActions));
        addCompareButton.click();

        System.out.println("Нажимаю на первый вариант сравнения...");
        By checkboxLocator = By.xpath("//mat-checkbox[.//input[contains(@id, 'mat-mdc-checkbox-10-input')]]");
        WebElement checkbox = wait.until(
                ExpectedConditions.elementToBeClickable(checkboxLocator)
        );
        checkbox.click();

        System.out.println("Нажимаю на применение изменений...");
        By listOfActionsWithCompare = By.xpath("//button[contains(@class, 'apply-button')]");
        WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(listOfActionsWithCompare));
        applyButton.click();


        By compareLine = By.xpath("//div[contains(text(), '#1')]");
        WebElement topCompare = wait.until(ExpectedConditions.visibilityOfElementLocated(compareLine));


        System.out.println("Тест успешно пройден!");
    }

    @Test
    @DisplayName("Функциональный тест раздела 'Администратор'")
    void testAdminModule() {
        driver.get("https://analytics.google.com/");

        System.out.println("Ожидание загрузки...");

        // 3. Ожидание загрузки сайта
        By dashboardLocator = By.xpath("//div[contains(@class, 'main-layout')]");
        WebElement dashboard = loginWait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLocator));

        System.out.println("Страница загружена! Запускаю авто-клики...");

        // 4. Переход в раздел "Администратор"
        System.out.println("Нажимаю на 'Администратор'...");
        By adminButtonLocator = By.xpath("//a[contains(@guidedhelpid, 'guided-help-admin-module')]");
        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(adminButtonLocator));
        adminButton.click();

        // 5. Переход в раздел "События"
        System.out.println("Нажимаю на 'События'...");
        By adminMenuLocator = By.xpath("//a[contains(@class, 'admin-events')]");
        WebElement eventLink = wait.until(ExpectedConditions.elementToBeClickable(adminMenuLocator));
        eventLink.click();


        // 5. Создание события
        System.out.println("Нажимаю на 'Создать событие'...");
        By eventMenuLocator = By.xpath("//button[contains(@class, 'pt-event-create-trigger')]");
        WebElement createEventButton = wait.until(ExpectedConditions.elementToBeClickable(eventMenuLocator));
        createEventButton.click();

        System.out.println("Ввожу название события...");

        WebElement titleEvent = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[contains(@class, 'pt-event-name')]")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                titleEvent
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].focus();",
                titleEvent
        );

        titleEvent.sendKeys("test");

        System.out.println("Ввожу url события...");

        WebElement urlEvent = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[contains(@class, 'pt-url-based-event-selector-field-input')]")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                urlEvent
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].focus();",
                urlEvent
        );

        urlEvent.sendKeys("google.com");


        System.out.println("Нажимаю на 'Создать'...");

        By eventCreateMenuLocator = By.xpath("//button[contains(@class, 'pt-create-button')]");
        WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(eventCreateMenuLocator));
        createButton.click();
    }
}