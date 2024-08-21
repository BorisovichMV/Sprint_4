package org.horosiyproekt.pages;

import org.horosiyproekt.enums.OrderBtnPlace;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private final WebDriver driver;
    private final String pageUrl= "https://qa-scooter.praktikum-services.ru/";
    private final By faqSection = By.xpath("//*[contains(@class, 'Home_FAQ')]");
    private final By faqItem = By.className("accordion__item");
    private final By question = By.className("accordion__button");
    private final By answer = By.className("accordion__panel");
    private final By cookieButtonOnBanner = By.id("rcc-confirm-button");
    private final By orderButton = By.xpath("//*[contains(@class, 'Button_Button')]");

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private WebElement getFaqSection() {
        return wait.until(driver -> driver.findElement(faqSection));
    }

    public void goToThisPage() {
        driver.get(pageUrl);
    }

    public List<WebElement> getAccordionItems() {
        return wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(faqSection, faqItem));
    }

    public WebElement getAccordionItemByIndex(Integer index) {
        return getAccordionItems().get(index);
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

    public WebElement getOrderButtonByPlace(OrderBtnPlace place) {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(place.getLocator(), orderButton));
    }

    public void clickOnOrderButton(WebElement orderButton) {
        closeCookieBanner();
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        orderButton.click();
    }
}
