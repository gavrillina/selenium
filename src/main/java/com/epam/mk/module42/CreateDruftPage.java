package com.epam.mk.module42;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateDruftPage extends AbstractPage {
	WebDriverWait wait = new WebDriverWait(driver, 5);

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

	private static final By drufts = By.xpath("//div[@ng-repeat = 'conversation in conversations track by conversation.ID']");
	private static final By druftListSender = By.xpath("//span[@class = 'senders-name']");
	private static final By druftListSubject = By.xpath("//span[@class = 'subject-text ellipsis']");
	private static final By druftListBody = By.xpath("html/body/div[1]");
	private static final By druftCloseButtonWait = By.xpath("//button[@ng-click='openCloseModal(message)']");

	// костыль
	private By senderKostyl(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[2]/span/span");
	}

	private By subjectKostyl(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[1]/h4/span[2]");
	}

	private By openKostyl(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]");
	}

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
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(drufts)));
		for (int i = 1; i <= driver.findElements(drufts).size(); i++) {
			if (driver.findElement(senderKostyl(i)).getText().equals(PropertiesLoader.getInfo("SENDER"))
					&& driver.findElement(subjectKostyl(i)).getText().equals(PropertiesLoader.getInfo("SUBJECT"))) {
				driver.findElement(openKostyl(i)).click();
				driver.switchTo().frame(druftFrame);
				if (driver.findElement(druftListBody).getText().equals(PropertiesLoader.getInfo("BODY"))) {
					System.out.println("The druft has been found");
					driver.switchTo().defaultContent();
					return new SendDruftPage(driver);
				} else {
					driver.switchTo().defaultContent();
					druftCloseButton.click();
					//wait.until(ExpectedConditions.invisibilityOfElementLocated(druftCloseButtonWait));
				}
			}
		}
		System.out.println("The druft has not been found");
		return null;
	}
}

// public SendDruftPage searchDruft() {
// druftPageButton.click(); // open druft folder
// wait.until(ExpectedConditions.visibilityOf(driver.findElement(drufts))); //
// ЖДАТЬ ПОКА ПОДГРУЗЯТСЯ ЧЕРНОВИКИ
// List<WebElement> druftList = driver.findElements(drufts);
// for (WebElement druft : druftList) {
// if
// (druft.findElement(druftListSender).getText().equals(PropertiesLoader.getInfo("SENDER"))
// // search email sender
// &&
// druft.findElement(druftListSubject).getText().equals(PropertiesLoader.getInfo("SUBJECT")))
// { // search email subject
// druft.click();
// driver.switchTo().frame(druftFrame);
// if
// (driver.findElement(druftListBody).getText().equals(PropertiesLoader.getInfo("BODY")))
// { // search email body
// System.out.println("The druft has been found");
// driver.switchTo().defaultContent();
// return new SendDruftPage(driver);
// } else {
// driver.switchTo().defaultContent();
// druftCloseButton.click();
// wait.until(ExpectedConditions.invisibilityOfElementLocated(druftCloseButtonWait));
// }
// }
// }
// System.out.println("The druft has not been found");
// return null;
// }
