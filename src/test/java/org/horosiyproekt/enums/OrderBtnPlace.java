package org.horosiyproekt.enums;

import org.openqa.selenium.By;

public enum OrderBtnPlace {
    TOP(By.xpath("//*[contains(@class, 'Home_Header')]")),
    BOTTOM(By.xpath("//*[contains(@class, 'Home_FinishButton')]"));

    private final By locator;

    OrderBtnPlace(By locator) {
        this.locator = locator;
    }

    public By getLocator() {
        return locator;
    }
}
