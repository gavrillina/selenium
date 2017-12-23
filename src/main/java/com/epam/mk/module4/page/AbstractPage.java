package com.epam.mk.module4.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage {
	protected WebDriver driver;
	private final int TIME_OUT_IN_SECONDS = 4;
	protected AbstractPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	protected void waitForElementVisible(WebElement webElement) {
		new WebDriverWait(driver, TIME_OUT_IN_SECONDS).until(ExpectedConditions.visibilityOf(webElement));
	}

	protected void waitForLocatorInvisible(By locator) {
		new WebDriverWait(driver, TIME_OUT_IN_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

}
