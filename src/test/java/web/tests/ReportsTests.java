package web.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.*;
import web.base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportsTests extends BaseTest {

    // Вспомогательный метод — используется в обоих тестах
    private void navigateToReports() {
        By reportsMenuLocator = By.xpath(
                "//a[contains(@guidedhelpid, 'guided-help-reports-module')]"
        );
        WebElement reportsButton = wait.until(
                ExpectedConditions.elementToBeClickable(reportsMenuLocator)
        );
        reportsButton.click();
    }

    @Test
    @DisplayName("Функциональный тест переключения на панели отчётов")
    void testLoadingReportsInterface() {
        navigateToReports();

        By reportHeader = By.xpath(
                "//h1 | //*[contains(text(), 'Сводка отчетов') " +
                        "or contains(text(), 'Reports snapshot')]"
        );
        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(reportHeader)
        );
        assertTrue(header.isDisplayed(), "Раздел отчетов не открылся!");
        System.out.println("Тест успешно пройден!");
    }

    @Test
    @DisplayName("Функциональный тест раздела 'Отчёты'")
    void testReportsModule() {
        navigateToReports();

        // Открытие виджета реального времени
        By menuOfReports = By.xpath(
                "//button[contains(@guidedhelpid, 'guided-help-realtime-overview')]"
        );
        WebElement realtimeBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(menuOfReports)
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", realtimeBtn
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", realtimeBtn
        );

        By widgetTab = By.xpath("//*[contains(@class, 'xap-card-content card-content')]");
        WebElement realtimeTab = wait.until(
                ExpectedConditions.visibilityOfElementLocated(widgetTab)
        );
        assertTrue(realtimeTab.isDisplayed(), "Виджет реального времени не открылся!");

        // Добавление сравнения
        By menuOfActions = By.xpath(
                "//button[contains(@class, 'report-filter add-comparison')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(menuOfActions)).click();

        By checkboxLocator = By.xpath(
                "//mat-checkbox[.//input[contains(@id, 'mat-mdc-checkbox-10-input')]]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(checkboxLocator)).click();

        By applyLocator = By.xpath("//button[contains(@class, 'apply-button')]");
        wait.until(ExpectedConditions.elementToBeClickable(applyLocator)).click();

        By compareLine = By.xpath("//div[contains(text(), '#1')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(compareLine));

        System.out.println("Тест успешно пройден!");
    }
    @Test
    @DisplayName("Проверка невозможности создать новый перечень сравнений без изменения исходного")
    void testImpossibleCreateNewComparisonWithoutChoice() {
        navigateToReports();

        // Открытие виджета реального времени
        By menuOfReports = By.xpath(
                "//button[contains(@guidedhelpid, 'guided-help-realtime-overview')]"
        );
        WebElement realtimeBtn = wait.until(
                ExpectedConditions.presenceOfElementLocated(menuOfReports)
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", realtimeBtn
        );
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", realtimeBtn
        );

        By widgetTab = By.xpath("//*[contains(@class, 'xap-card-content card-content')]");
        WebElement realtimeTab = wait.until(
                ExpectedConditions.visibilityOfElementLocated(widgetTab)
        );
        assertTrue(realtimeTab.isDisplayed(), "Виджет реального времени не открылся!");

        // Добавление сравнения
        By menuOfActions = By.xpath(
                "//button[contains(@class, 'report-filter add-comparison')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(menuOfActions)).click();

        By applyLocator = By.xpath("//button[contains(@class, 'mat-mdc-button-disabled')]");
        wait.until(ExpectedConditions.elementToBeClickable(applyLocator)).click();

        WebElement applyLocatorButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(applyLocator)
        );

        assertTrue(applyLocatorButton.isDisplayed(), "Кнопка активировано при неизменённом списке!");

        System.out.println("Тест успешно пройден!");
    }
}