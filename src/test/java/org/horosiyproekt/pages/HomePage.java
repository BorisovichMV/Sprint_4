package org.horosiyproekt.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private final WebDriver driver;
    private final By faqSection = By.xpath("//*[contains(@class, 'Home_FAQ')]");
    private final By faqItem = By.className("accordion__item");
    private final By question = By.className("accordion__button");
    private final By answer = By.className("accordion__panel");
    private final By cookieButtonOnBanner = By.id("rcc-confirm-button");
    private final By homeHeader = By.xpath("//*[contains(@class, 'Home_Header')]");
    private final By finish_button = By.xpath("//*[contains(@class, 'Home_FinishButton')]");
    private final By orderButton = By.xpath("//*[contains(@class, 'Button_Button')]");

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private WebElement getFaqSection() {
        return wait.until(driver -> driver.findElement(faqSection));
    }

    public List<WebElement> getAccordionItems() {
        return wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(faqSection, faqItem));
    }

    public WebElement getQuestion(WebElement accordionItem) {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(accordionItem, question));
    }

    public WebElement getAnswer(WebElement accordionItem) {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(accordionItem, answer));
    }

    public void clickOnQuestion(WebElement question) {
        closeCookieBanner();
        wait.until(ExpectedConditions.elementToBeClickable(question));
        question.click();
    }

    public void waitUntilAnswerIsVisible(WebElement answer) {
        try {
            wait.until(ExpectedConditions.visibilityOf(answer));
        }
        catch (TimeoutException ignored) {}
    }

    public void closeCookieBanner() {
        try {
            WebElement cookieButton = driver.findElement(cookieButtonOnBanner);
            if (cookieButton.isDisplayed()) {
                cookieButton.click();
            }
        }
        catch (NoSuchElementException ignored) {}
    }

    public WebElement getTopOrderButton() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(homeHeader, orderButton));
    }

    public WebElement getBottomOrderButton() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(finish_button, orderButton));
    }

    public void clickOnOrderButton(WebElement orderButton) {
        closeCookieBanner();
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        orderButton.click();
    }
}
