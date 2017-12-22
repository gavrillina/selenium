package com.epam.mk.module4.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.mk.module4.entity.Mail;
import com.epam.mk.module4.exception.DraftNotFoundException;

public class CreateDraftPage extends AbstractPage {
	WebDriverWait wait = new WebDriverWait(driver, 4);

	protected CreateDraftPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[@class='compose pm_button sidebar-btn-compose']")
	private WebElement draftCreateButton;

	@FindBy(xpath = "//input[@id='autocomplete']")
	private WebElement draftSenderInput;

	@FindBy(xpath = "//input[@ng-model='message.Subject']")
	private WebElement draftSubjectInput;

	@FindBy(xpath = "//iframe[@class = 'squireIframe']")
	private WebElement draftFrame;

	@FindBy(xpath = "html/body/div[1]")
	private WebElement draftFrameBodyInput;

	@FindBy(xpath = "//button[@ng-click='save(message, true, false)']")
	private WebElement draftSaveButton;

	@FindBy(xpath = "//button[@ng-click='openCloseModal(message)']")
	private WebElement draftCloseButton;

	@FindBy(xpath = "//a[@href='/drafts']")
	private WebElement draftsUrl;

	@FindBy(xpath = "//span[@ng-bind-html='$message']")
	private WebElement greenMessage;

	@FindBy(xpath = "//div[@ng-repeat = 'conversation in conversations track by conversation.ID']")
	private List<WebElement>draftList;

	private static final By DRAFT_CLOSE_BUTTON_WAIT = By.xpath("//button[@ng-click='openCloseModal(message)']");

	private By senderKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[2]/span/span");
	}

	private By subjectKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[1]/h4/span[2]");
	}

	private By openKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]");
	}

	public CreateDraftPage createDraft(Mail mail) {
		draftCreateButton.click();
		draftSubjectInput.sendKeys(mail.getSubject());
		draftSenderInput.sendKeys(mail.getSender());
		driver.switchTo().frame(draftFrame);
		new Actions(driver).sendKeys(draftFrameBodyInput, mail.getBody()).build().perform();
		driver.switchTo().defaultContent();
		draftSaveButton.click(); // save draft message
		wait.until(ExpectedConditions.visibilityOf(greenMessage));
		draftCloseButton.click(); // close draft message
		wait.until(ExpectedConditions.invisibilityOfElementLocated(DRAFT_CLOSE_BUTTON_WAIT));
		//System.out.println("The draft has been created");
		return this;
	}

	public SendDraftPage searchDraft(Mail mail) throws DraftNotFoundException {
		draftsUrl.click(); // open draft folder
		wait.until(ExpectedConditions.visibilityOf(draftList.get(0)));
		for (int i = 1; i <= draftList.size(); i++) {
			if (driver.findElement(senderKost(i)).getText().equals(mail.getSender())
					&& driver.findElement(subjectKost(i)).getText().equals(mail.getSubject())) {
				driver.findElement(openKost(i)).click();
				driver.switchTo().frame(draftFrame);
				if (draftFrameBodyInput.getText().equals(mail.getBody())) {
					//System.out.println("The draft has been found");
					driver.switchTo().defaultContent();
					return new SendDraftPage(driver);
				} else {
					driver.switchTo().defaultContent();
					draftCloseButton.click();				
					wait.until(ExpectedConditions.invisibilityOfElementLocated(DRAFT_CLOSE_BUTTON_WAIT));
					//System.out.println("The draft is skipped");
				}
			}
		}
		throw new DraftNotFoundException("The draft has not been found");
	}

//	private static final By druftListSender = By.xpath("//span[@class = 'senders-name']");
//	private static final By druftListSubject = By.xpath("//span[@class = 'subject-text ellipsis']");
	
//	 public SendDruftPage searchDruftOff() {
//		 druftPageButton.click(); // open druft folder
//		 wait.until(ExpectedConditions.visibilityOf(druftList.get(0))); // ЖДАТЬ ПОКА ПОДГРУЗЯТСЯ ЧЕРНОВИКИ
//		 for (WebElement druft : druftList) {
//			 if (druft.findElement(druftListSender).getText().equals(PropertiesLoader.getInfo("SENDER")) // search email sender
//					 &&
//					 druft.findElement(druftListSubject).getText().equals(PropertiesLoader.getInfo("SUBJECT"))) { // search email subject
//				 druft.click();
//				 driver.switchTo().frame(druftFrame);
//				 if (druftBodyInput.getText().equals(PropertiesLoader.getInfo("BODY"))) { // search email body
//					 //System.out.println("The druft has been found");
//					 driver.switchTo().defaultContent();
//					 return new SendDruftPage(driver);
//					 } else {
//						 driver.switchTo().defaultContent();
//						 druftCloseButton.click();
//						 wait.until(ExpectedConditions.invisibilityOfElementLocated(DRUFT_CLOSE_BUTTON_WAIT));
//						 }
//				 }
//			 }
//		 //System.out.println("The druft has not been found");
//		 return null;
//		 }
}
