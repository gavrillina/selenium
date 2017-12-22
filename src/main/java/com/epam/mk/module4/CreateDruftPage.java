package com.epam.mk.module4;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateDruftPage extends AbstractPage {
	WebDriverWait wait = new WebDriverWait(driver, 4);

	protected CreateDruftPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//button[@class='compose pm_button sidebar-btn-compose']")
	private WebElement druftCreateButton;

	@FindBy(xpath = "//input[@id='autocomplete']")
	private WebElement druftSenderInput;

	@FindBy(xpath = "//input[@ng-model='message.Subject']")
	private WebElement druftSubjectInput;

	@FindBy(xpath = "//iframe[@class = 'squireIframe']")
	private WebElement druftFrame;

	@FindBy(xpath = "html/body/div[1]")
	private WebElement druftBodyInput;

	@FindBy(xpath = "//button[@ng-click='save(message, true, false)']")
	private WebElement druftSaveButton;

	@FindBy(xpath = "//button[@ng-click='openCloseModal(message)']")
	private WebElement druftCloseButton;

	@FindBy(xpath = "//a[@href='/drafts']")
	private WebElement druftPageButton;

	@FindBy(xpath = "//span[@ng-bind-html='$message']")
	private WebElement greenPopup;

	@FindBy(xpath = "//div[@ng-repeat = 'conversation in conversations track by conversation.ID']")
	private List<WebElement>druftList;

	@SuppressWarnings("unused")
	private static final By druftListSender = By.xpath("//span[@class = 'senders-name']");
	@SuppressWarnings("unused")
	private static final By druftListSubject = By.xpath("//span[@class = 'subject-text ellipsis']");

	private static final By DRUFT_CLOSE_BUTTON_WAIT = By.xpath("//button[@ng-click='openCloseModal(message)']");

	private By senderKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[2]/span/span");
	}

	private By subjectKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[1]/h4/span[2]");
	}

	private By openKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]");
	}

	public CreateDruftPage createDruft(Mail mail) {
		druftCreateButton.click();
		druftSubjectInput.sendKeys(mail.getSubject());
		druftSenderInput.sendKeys(mail.getSender());
		driver.switchTo().frame(druftFrame);
		new Actions(driver).sendKeys(druftBodyInput, mail.getBody()).build().perform();
		driver.switchTo().defaultContent();
		druftSaveButton.click(); // save druft message
		wait.until(ExpectedConditions.visibilityOf(greenPopup));
		druftCloseButton.click(); // close druft message
		wait.until(ExpectedConditions.invisibilityOfElementLocated(DRUFT_CLOSE_BUTTON_WAIT));
		//System.out.println("The druft has been created");
		return this;
	}

	public SendDruftPage searchDruft(Mail mail) throws ProtonException {
		druftPageButton.click(); // open druft folder
		wait.until(ExpectedConditions.visibilityOf(druftList.get(0)));
		for (int i = 1; i <= druftList.size(); i++) {
			if (driver.findElement(senderKost(i)).getText().equals(mail.getSender())
					&& driver.findElement(subjectKost(i)).getText().equals(mail.getSubject())) {
				driver.findElement(openKost(i)).click();
				driver.switchTo().frame(druftFrame);
				if (druftBodyInput.getText().equals(mail.getBody())) {
					//System.out.println("The druft has been found");
					driver.switchTo().defaultContent();
					return new SendDruftPage(driver);
				} else {
					driver.switchTo().defaultContent();
					druftCloseButton.click();				
					wait.until(ExpectedConditions.invisibilityOfElementLocated(DRUFT_CLOSE_BUTTON_WAIT));
					//System.out.println("Skipped");
				}
			}
			
		}
		throw new ProtonException("The druft has not been found");
	}

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
