package com.epam.mk.module4;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SendDruftPage extends AbstractPage {

	protected SendDruftPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[@class='pm_button primary mobileFull composer-btn-send btnSendMessage-btn-action']")
	WebElement druftSendButton;

	@FindBy(xpath = "//a[@href='/sent']")
	WebElement sentPageButton;

	@FindBy(xpath = "//a[@href='/sent']")
	WebElement sentCloseButton;

	@FindBy(xpath = "//span[@ng-bind-html='$message']")
	WebElement greenPopup;
	By sentList = By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']");
	By sentListSender = By.xpath("//span[@class = 'senders-name']");	
	By sentListSubject = By.xpath("//span[@class = 'subject-text ellipsis']");	

	public String sendAction()  {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		druftSendButton.click();
		wait.until(ExpectedConditions.visibilityOf(greenPopup));
		System.out.println("The druft has been sent");
		// поиск в отправленных:
		sentPageButton.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(sentList)));
		List<WebElement> sents = (List<WebElement>) driver.findElements(sentList);
		for (WebElement sent : sents) {
			if (sent.findElement(sentListSender).getText().equals(PropertiesLoader.getInfo("SENDER")) // search email sender
					&& sent.findElement(sentListSubject).getText().equals(PropertiesLoader.getInfo("SUBJECT"))) { // search email subject
				sent.click();
				return "Now your email is in SENT folder";
			} else {
				driver.switchTo().defaultContent();
				sentCloseButton.click();
			}
		}
		return null;	
	}
}
