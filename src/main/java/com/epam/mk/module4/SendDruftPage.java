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
	private WebElement druftSendButton;

	@FindBy(xpath = "//a[@href='/sent']")
	private WebElement sentPageButton;

	@FindBy(xpath = "//a[@href='/sent']")
	private WebElement sentCloseButton;

	@FindBy(xpath = "//span[@ng-bind-html='$message']")
	private WebElement greenPopup;

	private final By SENTS = By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']");
	private final By SENT_SENDER = By.xpath("//span[@class = 'senders-name']");	
	private final By SENT_SUBJECT = By.xpath("//span[@class = 'subject-text ellipsis']");	

	public String sendAction()  {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		druftSendButton.click();
		wait.until(ExpectedConditions.visibilityOf(greenPopup));
		System.out.println("The druft has been sent");
		// поиск в отправленных:
		sentPageButton.click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(SENTS)));
		List<WebElement> sentList = (List<WebElement>) driver.findElements(SENTS);
		for (WebElement sent : sentList) {
			if (sent.findElement(SENT_SENDER).getText().equals(PropertiesLoader.getInfo("SENDER")) // search email sender
					&& sent.findElement(SENT_SUBJECT).getText().equals(PropertiesLoader.getInfo("SUBJECT"))) { // search email subject
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
