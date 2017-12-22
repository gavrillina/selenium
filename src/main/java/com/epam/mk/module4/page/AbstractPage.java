package com.epam.mk.module4.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {
	protected WebDriver driver;
	

	protected AbstractPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

    protected void waitForElementVisible(WebElement webElement) {
        new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(webElement));
    }
}
