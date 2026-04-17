package web.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import web.base.BaseTest;

public class MainInterfaceTests extends BaseTest {

    @Test
    @DisplayName("Функциональный тест загрузки главного интерфейса")
    void testLoadingMainInterface() {
        By dashboardLocator = By.xpath("//div[contains(@class, 'main-layout')]");
        WebElement dashboard = loginWait.until(
                ExpectedConditions.visibilityOfElementLocated(dashboardLocator)
        );
        System.out.println("Тест успешно пройден!");
    }
}