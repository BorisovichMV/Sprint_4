package org.horosiyproekt.test;

import org.horosiyproekt.pages.HomePage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RedirectToOrderTest {
    private WebDriver driver;

    @Test
    public void checkRedirectToOrderByClickOnTopOrderButton() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        String homePageUrl = "https://qa-scooter.praktikum-services.ru/";
        String orderPageUrl = "https://qa-scooter.praktikum-services.ru/order";
        driver.get(homePageUrl);

        HomePage homePage = new HomePage(driver);

        WebElement topOrderButton = homePage.getTopOrderButton();
        homePage.clickOnOrderButton(topOrderButton);

        String location = driver.getCurrentUrl();

        Assert.assertEquals(orderPageUrl, location);
    }

    @Test
    public void checkRedirectToOrderByClickOnBottomOrderButton() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        String homePageUrl = "https://qa-scooter.praktikum-services.ru/";
        String orderPageUrl = "https://qa-scooter.praktikum-services.ru/order";
        driver.get(homePageUrl);

        HomePage homePage = new HomePage(driver);

        WebElement bottomOrderButton= homePage.getBottomOrderButton();
        homePage.clickOnOrderButton(bottomOrderButton);

        String location = driver.getCurrentUrl();

        Assert.assertEquals(orderPageUrl, location);
    }

    @After
    public void teardown() {
        // Закрой браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
