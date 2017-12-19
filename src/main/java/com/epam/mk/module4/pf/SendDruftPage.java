package com.epam.mk.module4.pf;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.epam.mk.module4.PropertiesLoader;

public class SendDruftPage extends MyDriver {

	protected SendDruftPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[@class='pm_button primary mobileFull composer-btn-send btnSendMessage-btn-action']")
	WebElement druftSendButton;
	
	@FindBy(xpath = "//a[@href='/sent']")
	WebElement sentPageButton;
	
	
	@FindBy(xpath = "//a[@href='/sent']")
	WebElement sentCloseButton;

	By sentList = By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']");
	By sentListSender = By.xpath("//span[@class = 'senders-name']");	
	By sentListSubject = By.xpath("//span[@class = 'subject-text ellipsis']");	

	public String sendDruft() throws InterruptedException {
		druftSendButton.click();
		System.out.println("The druft has been sended");
		// поиск в отправленных:
		sentPageButton.click();
		Thread.sleep(2000);
		List<WebElement> sents = (List<WebElement>) driver.findElements(sentList);
		for (WebElement sent : sents) {
			if (sent.findElement(sentListSender).getText().equals(PropertiesLoader.getInfo("SENDER")) // search email sender
					&& sent.findElement(sentListSubject).getText().equals(PropertiesLoader.getInfo("SUBJECT"))) { // search email subject
				return "Now your email is in SENT folder";
			} else {
				driver.switchTo().defaultContent();
				sentCloseButton.click();
			}
		}
		return null;	
	}
}
