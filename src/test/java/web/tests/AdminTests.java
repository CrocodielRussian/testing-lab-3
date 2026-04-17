package web.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.*;
import web.base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminTests extends BaseTest {

    // Вспомогательный метод — навигация в раздел Администратор
    private void navigateToAdmin() {
        By adminButtonLocator = By.xpath(
                "//a[contains(@guidedhelpid, 'guided-help-admin-module')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(adminButtonLocator)).click();
    }

    private void assertAdminPanelLoaded() {
        By adminPanelLocator = By.xpath("//h1[contains(text(), 'Администратор')]");
        WebElement adminPanel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(adminPanelLocator)
        );
        assertTrue(adminPanel.isDisplayed(), "Панель администратора не загрузилась!");
    }

    @Test
    @DisplayName("Функциональный тест переключения на панель администратора")
    void testLoadingAdminInterface() {
        navigateToAdmin();
        assertAdminPanelLoaded();
        System.out.println("Тест успешно пройден!");
    }

    @Test
    @DisplayName("Функциональный тест раздела 'Администратор'")
    void testAdminModule() {
        navigateToAdmin();

        // Переход в События
        By adminMenuLocator = By.xpath("//a[contains(@class, 'admin-events')]");
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuLocator)).click();

        // Создание события
        By eventMenuLocator = By.xpath(
                "//button[contains(@class, 'pt-event-create-trigger')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(eventMenuLocator)).click();

        // Название
        WebElement titleEvent = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@class, 'pt-event-name')]")
        ));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", titleEvent
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", titleEvent);
        titleEvent.sendKeys("test");

        // URL
        WebElement urlEvent = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@class, 'pt-event-trigger-url-input')]")
        ));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", urlEvent
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", urlEvent);
        urlEvent.sendKeys("google.com");

        // Подтверждение
        By createLocator = By.xpath("//button[contains(@class, 'pt-create-button')]");
        wait.until(ExpectedConditions.elementToBeClickable(createLocator)).click();

        System.out.println("Тест успешно пройден!");
    }

    @Test
    @DisplayName("Проверка невозможности создать тест без url")
    void testImpossibleCreateEventWithoutUrl() {
        navigateToAdmin();

        // Переход в События
        By adminMenuLocator = By.xpath("//a[contains(@class, 'admin-events')]");
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuLocator)).click();

        // Создание события
        By eventMenuLocator = By.xpath(
                "//button[contains(@class, 'pt-event-create-trigger')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(eventMenuLocator)).click();

        // Название
        WebElement titleEvent = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@class, 'pt-event-name')]")
        ));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", titleEvent
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", titleEvent);

        By createLocator = By.xpath("//button[contains(@class, 'pt-create-button')]");
        wait.until(ExpectedConditions.elementToBeClickable(createLocator)).click();

        By errorTitle = By.xpath("//*[@id=\"mat-mdc-error-1\"]");
        WebElement errorTitleText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorTitle)
        );

        assertTrue(errorTitleText.isDisplayed(), "Нет ошибки при создании события без текста!");


        System.out.println("Тест успешно пройден!");
    }

    @Test
    @DisplayName("Проверка невозможности создать тест без названия")
    void testImpossibleCreateEventWithoutTitle() {
        navigateToAdmin();

        // Переход в События
        By adminMenuLocator = By.xpath("//a[contains(@class, 'admin-events')]");
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuLocator)).click();

        // Создание события
        By eventMenuLocator = By.xpath(
                "//button[contains(@class, 'pt-event-create-trigger')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(eventMenuLocator)).click();

        // Название
        WebElement titleEvent = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@class, 'pt-event-name')]")
        ));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", titleEvent
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", titleEvent);
        titleEvent.sendKeys("test");

        By createLocator = By.xpath("//button[contains(@class, 'pt-create-button')]");
        wait.until(ExpectedConditions.elementToBeClickable(createLocator)).click();

        By errorTitle = By.xpath("//*[@id=\"mat-mdc-error-0\"]");
        WebElement errorTitleText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorTitle)
        );

        assertTrue(errorTitleText.isDisplayed(), "Нет ошибки при создании события без текста!");


        System.out.println("Тест успешно пройден!");
    }


    @Test
    @DisplayName("Тест захода на страницу администратора через URL")
    void testAlternativeWayAdminModule() {
        String newUrl = driver.getCurrentUrl()
                .replace("reports/intelligenthome", "admin");
        driver.get(newUrl);

        assertAdminPanelLoaded();
        System.out.println("Тест успешно пройден!");
    }
}