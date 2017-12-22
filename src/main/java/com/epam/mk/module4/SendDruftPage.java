package com.epam.mk.module4;

import java.util.List;

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
	
	@FindBy(xpath = "//*[@ng-repeat = 'conversation in conversations track by conversation.ID']")
	private List<WebElement>sentList;

	@FindBy(xpath = "//span[@class = 'senders-name']")
	private WebElement sentSenderSpan;

	@FindBy(xpath = "//span[@class = 'subject-text ellipsis']")
	private WebElement sentSubjectSpan;

	public String sendAction(Mail mail) throws ProtonException {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		druftSendButton.click();
		wait.until(ExpectedConditions.visibilityOf(greenPopup));
		//System.out.println("The druft has been sent");
		// поиск в отправленных:
		sentPageButton.click();
		wait.until(ExpectedConditions.visibilityOf(sentList.get(0)));
		for (WebElement sent : sentList) {
			if (sentSenderSpan.getText().equals(mail.getSender()) // search email sender
					&& sentSubjectSpan.getText().equals(mail.getSubject())) { // search email subject
				sent.click();
				return "Now your email is in SENT folder";
			} else {
				driver.switchTo().defaultContent();
				sentCloseButton.click();
			}
		}
		throw new ProtonException("The sent has not been found");	
	}

//	private final By SENTS = By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']");
//	private final By SENT_SENDER = By.xpath("//span[@class = 'senders-name']");
//	private final By SENT_SUBJECT = By.xpath("//span[@class = 'subject-text ellipsis']");	
	
	
}
