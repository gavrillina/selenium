package com.epam.mk.module4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")
	private WebElement loginPageButton;

	@FindBy(xpath = "//input[@id='username']")
	private WebElement usernameInput;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement passwordInput;

	@FindBy(xpath = "//button[@id='login_btn']")
	private WebElement enterButton;

	@FindBy(xpath = "//div[@ng-if='showWelcome']/header")
	private WebElement welcomeText;

	public LoginPage openUrl() {
		driver.get(PropertiesLoader.getInfo("URL"));
		return this;
	}

	public CreateDruftPage loginAction() {
		loginPageButton.click();
		usernameInput.sendKeys(PropertiesLoader.getInfo("USERNAME"));
		passwordInput.sendKeys(PropertiesLoader.getInfo("PASSWORD"));
		enterButton.click();
		if (welcomeText.getText().equals(PropertiesLoader.getInfo("WELCOME"))) {
			return new CreateDruftPage(driver);
		} else
			return null;
	}
}
