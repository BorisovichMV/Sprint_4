package org.horosiyproekt.test;

import org.horosiyproekt.pages.HomePage;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FaqSectionTest {
    private WebDriver driver;

    @Test
    public void checkFaqSection() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        driver.get("https://qa-scooter.praktikum-services.ru/");

        HomePage homePage = new HomePage(driver);

        List<WebElement> accordionItems = homePage.getAccordionItems();
        accordionItems.forEach(accordionItem -> {
            WebElement question = homePage.getQuestion(accordionItem);
            WebElement answer = homePage.getAnswer(accordionItem);

            assertFalse("Ответ на вопрос: \"" + question.getText() + "\" видим до клика на вопрос.", answer.isDisplayed());

            homePage.clickOnQuestion(question);
            homePage.waitUntilAnswerIsVisible(answer);
            assertTrue("Ответ на вопрос: \"" + question.getText() + "\" невидим после клика на вопрос.", answer.isDisplayed());

        });

    }

    @After
    public void teardown() {
        // Закрой браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
