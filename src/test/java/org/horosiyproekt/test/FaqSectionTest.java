package org.horosiyproekt.test;

import org.horosiyproekt.enums.OrderBtnPlace;
import org.horosiyproekt.pages.HomePage;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqSectionTest {
    private WebDriver driver;
    private Integer accordionItemIdx;

    public FaqSectionTest(Integer accordionItemIdx) {
        this.accordionItemIdx = accordionItemIdx;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                { 0 },
                { 1 },
                { 2 },
                { 3 },
                { 4 },
                { 5 },
                { 6 },
                { 7 }
        };
    }


    @Test
    public void checkFaqSection() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        HomePage homePage = new HomePage(driver);
        homePage.goToThisPage();

        WebElement accordionItem = homePage.getAccordionItemByIndex(accordionItemIdx);
        WebElement question = homePage.getQuestion(accordionItem);
        WebElement answer = homePage.getAnswer(accordionItem);

        assertFalse("Ответ на вопрос: \"" + question.getText() + "\" видим до клика на вопрос.", answer.isDisplayed());

        homePage.clickOnQuestion(question);
        homePage.waitUntilAnswerIsVisible(answer);
        assertTrue("Ответ на вопрос: \"" + question.getText() + "\" невидим после клика на вопрос.", answer.isDisplayed());
    }

    @After
    public void teardown() {
        // Закрой браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
