package com.epam.mk.module42;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.mk.module41.PropertiesLoader;

public class CreateDruftPage extends AbstractPage {
	WebDriverWait wait = new WebDriverWait(driver, 5);
	protected CreateDruftPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[@class='compose pm_button sidebar-btn-compose']")
	WebElement druftCreateButton;

	@FindBy(xpath = "//input[@id='autocomplete']")
	WebElement druftSenderInput;

	@FindBy(xpath = "//input[@ng-model='message.Subject']")
	WebElement druftSubjectInput;

	@FindBy(xpath = "//iframe[@class = 'squireIframe']")
	WebElement druftFrame;

	@FindBy(xpath = "html/body/div[1]")
	WebElement druftBodyInput;

	@FindBy(xpath = "//button[@ng-click='save(message, true, false)']")
	WebElement druftSaveButton;

	@FindBy(xpath = "//button[@ng-click='openCloseModal(message)']")
	WebElement druftCloseButton;

	@FindBy(xpath = "//a[@href='/drafts']")
	WebElement druftPageButton;

	@FindBy(xpath = "//span[@ng-bind-html='$message']")
	WebElement greenPopup;

	By druftList = By.xpath("//div[@ng-repeat = 'conversation in conversations track by conversation.ID']");
	By druftListSender = By.xpath("//span[@class = 'senders-name']");
	By druftListSubject = By.xpath("//span[@class = 'subject-text ellipsis']");
	By druftListBody = By.xpath("html/body/div[1]");
	By druftCloseButtonWait = By.xpath("//button[@ng-click='openCloseModal(message)']");

	
	public CreateDruftPage createDruft() {
		druftCreateButton.click();
		new Actions(driver).sendKeys(druftSubjectInput, PropertiesLoader.getInfo("SUBJECT")).build().perform();
		druftSenderInput.sendKeys(PropertiesLoader.getInfo("SENDER"));
		driver.switchTo().frame(druftFrame);
		new Actions(driver).sendKeys(druftBodyInput, PropertiesLoader.getInfo("BODY")).build().perform();
		driver.switchTo().defaultContent();
		druftSaveButton.click(); // save druft message
		wait.until(ExpectedConditions.visibilityOf(greenPopup));
		druftCloseButton.click(); // close druft message
		wait.until(ExpectedConditions.invisibilityOfElementLocated(druftCloseButtonWait));
		System.out.println("The druft has been created");
		return this;
	}

	public SendDruftPage searchDruft() {
		druftPageButton.click(); // open druft folder
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(druftList))); // ЖДАТЬ ПОКА ПОДГРУЗЯТСЯ ЧЕРНОВИКИ
		List<WebElement> drufts = driver.findElements(druftList);
		for (WebElement druft : drufts) {
			if (druft.findElement(druftListSender).getText().equals(PropertiesLoader.getInfo("SENDER")) // search email sender
					&& druft.findElement(druftListSubject).getText().equals(PropertiesLoader.getInfo("SUBJECT"))) { // search email subject
				druft.click();
				driver.switchTo().frame(druftFrame);
				if (driver.findElement(druftListBody).getText().equals(PropertiesLoader.getInfo("BODY"))) { // search email body
					System.out.println("The druft has been found");
					driver.switchTo().defaultContent();
					return new SendDruftPage(driver);
				} else {
					driver.switchTo().defaultContent();
					druftCloseButton.click();
					wait.until(ExpectedConditions.invisibilityOfElementLocated(druftCloseButtonWait));
				}
			}
		}
		System.out.println("The druft has not been found");
		return null;
	}
}
