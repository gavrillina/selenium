package com.epam.mk.module4.pf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.epam.mk.module4.PropertiesLoader;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")
	WebElement loginPageButton;

	@FindBy(xpath = "//input[@id='username']")
	WebElement usernameInput;

	@FindBy(xpath = "//input[@id='password']")
	WebElement passwordInput;

	@FindBy(xpath = "//button[@id='login_btn']")
	WebElement enterButton;
	
	@FindBy(xpath = "//div[@ng-if='showWelcome']/header")
	WebElement welcomeText;

	public LoginPage openUrl() {
		driver.get(PropertiesLoader.getInfo("URL"));
		return this;
	}

	public CreateDruftPage openPage() {
		loginPageButton.click();
		usernameInput.sendKeys(PropertiesLoader.getInfo("USERNAME"));
		passwordInput.sendKeys(PropertiesLoader.getInfo("PASSWORD"));
		enterButton.click();
		return new CreateDruftPage(driver);
	}
}
