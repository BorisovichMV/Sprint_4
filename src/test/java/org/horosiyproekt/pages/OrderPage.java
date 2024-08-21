package org.horosiyproekt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPage {
    private final WebDriver driver;
    private final String pageUrl = "https://qa-scooter.praktikum-services.ru/order";
    private final WebDriverWait wait;

    private final By orderFormLocator = By.xpath("//div[contains(@class, 'Order_Form')]");
    private final By nameFieldLocator = By.xpath("//input[contains(@placeholder, 'Имя')]");
    private final By lastNameFieldLocator = By.xpath("//input[contains(@placeholder, 'Фамилия')]");
    private final By addressFieldLocator = By.xpath("//input[contains(@placeholder, 'Адрес')]");
    private final By undergroundFieldLocator = By.xpath("//input[contains(@placeholder, 'Станция')]");
    private final By undergroundItemLocator = By.xpath("//li[contains(@class, 'select-search__row')]");
    private final By phoneFieldLocator = By.xpath("//input[contains(@placeholder, 'Телефон')]");

    private final By whenFieldLocator = By.xpath("//div[contains(@class, 'Order_MixedDatePicker')]");
    private final By previousMonthBtnLocator = By.xpath("//button[contains(@aria-label, 'Previous Month')]");
    private final By nextMonthBtnLocator = By.xpath("//button[contains(@aria-label, 'Next Month')]");
    private final By currentMonthLocator = By.className("react-datepicker__current-month");
    private final By datePickerDayLocator = By.xpath("//div[contains(@class,'react-datepicker__day')]");

    private final By durationFieldLocator = By.xpath("//div[contains(@class, 'Dropdown-root')]");
    private final By durationDropdownMenuLocator = By.className("Dropdown-menu");
    private final By durationDropdownMenuItemLocator = By.xpath("//div[contains(@class, 'Dropdown-option')]");

    private final By commentFieldLocator = By.xpath("//input[contains(@placeholder, 'Комментарий')]");

    private final By blackCheckBoxFieldLocator = By.id("black");
    private final By greyCheckBoxFieldLocator = By.id("grey");

    private final By nextBtnPlace = By.xpath("//div[contains(@class, 'Order_NextButton')]");
    private final By nextBtnLocator = By.xpath("//button[contains(@class, 'Button_Middle')]");
    private final By orderBtnPlace = By.xpath("//div[contains(@class, 'Order_Buttons')]");
    private final By orderBtnLocator = By.xpath("//button[contains(@class, 'Button_Middle') and not(contains(@class, 'Button_Inverted'))]");
    private final By confirmOrderBtnPlaceLocator = By.xpath("//div[contains(@class, 'Order_Modal')]");
    private final By confirmOrderBtnLocator = By.xpath("//button[contains(@class, 'Button_Middle') and not(contains(@class, 'Button_Inverted'))]");

    private final By orderModalLocator = By.xpath("//div[contains(@class, 'Order_Modal')]");
    private final By closeOrderModalBtnLocator = By.xpath("//div[contains(@class, 'Order_NextButton')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    public void goToThisPage() {
        driver.get(pageUrl);
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public WebElement getNameField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, nameFieldLocator));
    }
    public WebElement getLastNameField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, lastNameFieldLocator));
    }
    public WebElement getAddressField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, addressFieldLocator));
    }
    public WebElement getUndergroundField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, undergroundFieldLocator));
    }
    public WebElement getPhoneField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, phoneFieldLocator));
    }
    public WebElement getWhenField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, whenFieldLocator));
    }
    public WebElement getDurationField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, durationFieldLocator));
    }
    public WebElement getCommentField() {
        return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderFormLocator, commentFieldLocator));
    }
    public WebElement getBlackCheckbox() {
        return wait.until(ExpectedConditions.elementToBeClickable(blackCheckBoxFieldLocator));
    }
    public WebElement getGrayCheckbox() {
        return wait.until(ExpectedConditions.elementToBeClickable(greyCheckBoxFieldLocator));
    }

    public void fillTextField(WebElement field, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(field));
        field.sendKeys(value);
    }

    public void clickOnCheckbox(String color) {
        WebElement checkBox;
        if (color.equals("black")) {
            wait.until(ExpectedConditions.elementToBeClickable(getBlackCheckbox())).click();
        } else if (color.equals("grey")){
            wait.until(ExpectedConditions.elementToBeClickable(getGrayCheckbox())).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(getBlackCheckbox())).click();
            wait.until(ExpectedConditions.elementToBeClickable(getGrayCheckbox())).click();
        }
    }

    public void fillWhenField(Integer date, Integer month, Integer year, String targetMonth) {
        WebElement whenField = getWhenField();
        whenField.click();
        WebElement changeMonthBtn;
        WebElement currentMonth = wait.until(ExpectedConditions.elementToBeClickable(currentMonthLocator));

        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        LocalDate targetDate = LocalDate.of(year, month, date);

        if (targetDate.isBefore(currentDate)) {
            changeMonthBtn = wait.until(ExpectedConditions.elementToBeClickable(previousMonthBtnLocator));
        } else {
            changeMonthBtn = wait.until(ExpectedConditions.elementToBeClickable(nextMonthBtnLocator));
        }

        while (!currentMonth.getText().equals(targetMonth + " " + year)) {
            String initialText = currentMonth.getText();
            changeMonthBtn.click();
            wait.until(driver -> !currentMonth.getText().equals(initialText));
        }

        List<WebElement> targetDateBtns = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(datePickerDayLocator))
                .stream()
                .filter(element -> element.getText().equals(String.valueOf(date)))
                .filter(element -> element.getAttribute("aria-label").contains(targetMonth.substring(0, targetMonth.length()-1)))
                .collect(Collectors.toList());

        targetDateBtns.get(0).click();
    }

    public void fillDurationField(Integer days) {
        List<String> duration = List.of("сутки", "двое суток", "трое суток", "четверо суток", "пятеро суток", "шестеро суток", "семеро суток");
        String targetDurationStr = duration.get(days - 1);
        WebElement durationField = getDurationField();
        durationField.click();
        List<WebElement> durationDropdownMenuItems = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(durationDropdownMenuLocator, durationDropdownMenuItemLocator))
                .stream()
                .filter(element -> element.getText().equals(targetDurationStr))
                .collect(Collectors.toList());
        durationDropdownMenuItems.get(0).click();
    }

    public void fillUndergroundField() {
        WebElement undergroundField = getUndergroundField();
        undergroundField.click();
        List<WebElement> undergroundDropdownMenuItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(undergroundItemLocator));
        undergroundDropdownMenuItems.get(0).click();
    }

    public WebElement waitForOrderModal() {
        return wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(orderModalLocator, closeOrderModalBtnLocator)).get(0);
    }

    public void clickOnNextBtn() {
        WebElement nextBtn = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(nextBtnPlace, nextBtnLocator));
        nextBtn.click();
    }

    public void clickOnOrderBtn() {
        WebElement orderBtn = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(orderBtnPlace, orderBtnLocator));
        orderBtn.click();
    }

    public void clickOnConfirmOrderBtn() {
        WebElement confirmOrderBtn = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(confirmOrderBtnPlaceLocator, confirmOrderBtnLocator)).get(1);
        confirmOrderBtn.click();
    }
}
