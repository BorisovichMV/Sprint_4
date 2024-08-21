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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@RunWith(Parameterized.class)
public class OrderTest {
    private final String name;
    private final String lastName;
    private final String address;
    private final String phone;
    private final Integer date;
    private final Integer month;
    private final Integer year;
    private final String targetMonth;
    private final Integer duration;
    private final String color;
    private final String comment;
    private final Boolean isValid;
    private final OrderBtnPlace orderBtnPlace;
    private WebDriver driver;


    public OrderTest(
            String name,
            String lastName,
            String address,
            String phone,
            Integer date,
            Integer month,
            Integer year,
            String targetMonth,
            Integer duration,
            String color,
            String comment,
            Boolean isValid,
            OrderBtnPlace orderBtnPlace
    ) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.month = month;
        this.year = year;
        this.targetMonth = targetMonth;
        this.duration = duration;
        this.color = color;
        this.comment = comment;
        this.isValid = isValid;
        this.orderBtnPlace = orderBtnPlace;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"Иван", "Иванов", "ул. Ленина, д. 10", "12345678901", 20, 9, 2024, "сентябрь", 1, "black", "no comment", true, OrderBtnPlace.TOP},
                {"Иван", "Иванов", "ул. Ленина, д. 10", "+12345678901", 18, 8, 2024, "август", 3, "grey", "коммент по-RUSSки", true, OrderBtnPlace.TOP},
                {"Иван", "Иванов", "ул. Ленина, д. 10", "+12345678901", 9, 5, 2025, "май", 7, "both", "", true, OrderBtnPlace.BOTTOM},
                {"Иван", "Иванов", "ул. Ленина, д. 10", "12345678901", 9, 5, 2024, "май", 5, "both", "", true, OrderBtnPlace.BOTTOM},
        };
    }

    @Test
    public void orderTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

//        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--no-sandbox", "--disable-dev-shm-usage"); // "--headless",
//        driver = new FirefoxDriver(options);


        HomePage homePage = new HomePage(driver);
        OrderPage orderPage = new OrderPage(driver);

        homePage.goToThisPage();

        WebElement topOrderButton = homePage.getOrderButtonByPlace(orderBtnPlace);
        homePage.clickOnOrderButton(topOrderButton);

        String location = driver.getCurrentUrl();

        Assert.assertEquals(orderPage.getPageUrl(), location);

        WebElement nameField = orderPage.getNameField();
        WebElement lastNameField = orderPage.getLastNameField();
        WebElement addressField = orderPage.getAddressField();
        WebElement phoneField = orderPage.getPhoneField();

        orderPage.fillTextField(nameField, name);
        orderPage.fillTextField(lastNameField, lastName);
        orderPage.fillTextField(addressField, address);
        orderPage.fillUndergroundField();
        orderPage.fillTextField(phoneField, phone);

        orderPage.clickOnNextBtn();

        orderPage.fillWhenField(date, month, year, targetMonth);
        orderPage.fillDurationField(duration);
        orderPage.clickOnCheckbox(color);

        WebElement commentField = orderPage.getCommentField();
        orderPage.fillTextField(commentField, comment);

        orderPage.clickOnOrderBtn();
        orderPage.clickOnConfirmOrderBtn();

        WebElement orderModal = orderPage.waitForOrderModal();
        Assert.assertEquals(isValid, orderModal.isDisplayed());
    }

    @After
    public void teardown() {
        // Закрой браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
