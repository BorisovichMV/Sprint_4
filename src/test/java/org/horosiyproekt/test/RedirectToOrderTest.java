package org.horosiyproekt.test;

import org.horosiyproekt.enums.OrderBtnPlace;
import org.horosiyproekt.pages.HomePage;
import org.horosiyproekt.pages.OrderPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@RunWith(Parameterized.class)
public class RedirectToOrderTest {
    private final OrderBtnPlace buttonPlace;
    private WebDriver driver;

    public RedirectToOrderTest(OrderBtnPlace place) {
        this.buttonPlace = place;
    }

    @Parameterized.Parameters
    public static Object [][] getParameters() {
        return new Object[][] {
                { OrderBtnPlace.TOP },
                { OrderBtnPlace.BOTTOM }
        };
    }

    @Test
    public void checkRedirectToOrderByClickOnButton() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        HomePage homePage = new HomePage(driver);
        OrderPage orderPage = new OrderPage(driver);
        homePage.goToThisPage();

        WebElement orderButton = homePage.getOrderButtonByPlace(buttonPlace);
        homePage.clickOnOrderButton(orderButton);

        String location = driver.getCurrentUrl();

        Assert.assertEquals(orderPage.getPageUrl(), location);
    }

    @After
    public void teardown() {
        // Закрой браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
