package com.epam.mk.module4.page.move;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.epam.mk.module4.PropertyLoader;
import com.epam.mk.module4.exception.CannotLoginException;
import com.epam.mk.module4.page.AbstractPage;

public class MoveLoginPage extends AbstractPage {

	public MoveLoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")
	private WebElement loginUrlButton;

	@FindBy(xpath = "//input[@id='username']")
	private WebElement usernameInput;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement passwordInput;

	@FindBy(xpath = "//button[@id='login_btn']")
	private WebElement loginButton;

	@FindBy(xpath = "//div[@ng-if='showWelcome']/header")
	private WebElement welcomeMessage;

	public MoveLoginPage openUrl() {
		driver.get(PropertyLoader.getInfo("URL"));
		return this;
	}

	public MoveItemPage loginAction() throws CannotLoginException {
		loginUrlButton.click();
		usernameInput.sendKeys(PropertyLoader.getInfo("USERNAME"));
		passwordInput.sendKeys(PropertyLoader.getInfo("PASSWORD"));
		loginButton.click();
		if (welcomeMessage.isDisplayed()) {
			return new MoveItemPage(driver);
		} else
			throw new CannotLoginException("Login failed");
	}
}
