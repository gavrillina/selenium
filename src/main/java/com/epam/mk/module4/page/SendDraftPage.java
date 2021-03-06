package com.epam.mk.module4.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.epam.mk.module4.entity.Mail;
import com.epam.mk.module4.exception.DraftNotFoundException;

public class SendDraftPage extends AbstractPage {

	protected SendDraftPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[@class='pm_button primary mobileFull composer-btn-send btnSendMessage-btn-action']")
	private WebElement draftSendButton;

	@FindBy(xpath = "//a[@href='/sent']")
	private WebElement sentFolderUrl;

	@FindBy(xpath = "//span[@ng-bind-html='$message']")
	private WebElement greenMessage;

	@FindBy(xpath = "//div[@ng-repeat = 'conversation in conversations track by conversation.ID']")
	private List<WebElement>sentList;

	@FindBy(xpath = "//span[@class = 'senders-name']")
	private WebElement sentSenderSpan;

	@FindBy(xpath = "//span[@class = 'subject-text ellipsis']")
	private WebElement sentSubjectSpan;

	public String sendDraft(Mail mail) throws DraftNotFoundException {
		draftSendButton.click();
		waitForElementVisible(greenMessage);
		//System.out.println("The draft has been sent");
		// поиск в отправленных:
		sentFolderUrl.click();
		waitForElementVisible(sentList.get(0));
		for (WebElement sent : sentList) {
			if (sentSenderSpan.getText().equals(mail.getSender()) // search email sender
					&& sentSubjectSpan.getText().equals(mail.getSubject())) { // search email subject
				sent.click();
				return "Now your email is in SENT folder";
			}
		}
		throw new DraftNotFoundException("The sent has not been found");
	}

//	private final By SENTS = By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']");
//	private final By SENT_SENDER = By.xpath("//span[@class = 'senders-name']");
//	private final By SENT_SUBJECT = By.xpath("//span[@class = 'subject-text ellipsis']");	

}
