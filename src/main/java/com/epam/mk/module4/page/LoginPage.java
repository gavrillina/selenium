package com.epam.mk.module4.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.epam.mk.module4.PropertiesLoader;
import com.epam.mk.module4.exception.CannotLoginException;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
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

	public LoginPage openUrl() {
		driver.get(PropertiesLoader.getInfo("URL"));
		return this;
	}

	public CreateDraftPage loginAction() throws CannotLoginException {
		loginUrlButton.click();
		usernameInput.sendKeys(PropertiesLoader.getInfo("USERNAME"));
		passwordInput.sendKeys(PropertiesLoader.getInfo("PASSWORD"));
		loginButton.click();
		if (welcomeMessage.isDisplayed()) {
			return new CreateDraftPage(driver);
		} else
			throw new CannotLoginException("Login failed");
	}
}
