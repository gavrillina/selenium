package com.epam.mk.module4;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class SeleniumAction {

	static WebDriver driver = new SeleniumDriver().chromeDrv();

	public static void main(String[] args) {
		loginMail(
				PropertiesLoader.getInfo("URL"),
				PropertiesLoader.getInfo("USERNAME"),
				PropertiesLoader.getInfo("PASSWORD"));
		try {
			createDruftAndCheck(
					PropertiesLoader.getInfo("SENDER"),
					PropertiesLoader.getInfo("SUBJECT"),
					PropertiesLoader.getInfo("BODY"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			sendDruftAndCheck(
					PropertiesLoader.getInfo("SENDER"),
					PropertiesLoader.getInfo("SUBJECT"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String loginMail(String url, String username, String password) {
		String welcomeText = "";
		driver.get(url);
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		welcomeText = driver.findElement(By.xpath("//div[@ng-if='showWelcome']/header")).getText();
		System.out.print(welcomeText);
		return welcomeText;
	}

	public static boolean createDruftAndCheck(String sender, String subject, String body) throws InterruptedException {
		driver.findElement(By.xpath("//button[@class='compose pm_button sidebar-btn-compose']")).click();
		driver.findElement(By.xpath("//input[@id='autocomplete']")).sendKeys(sender);
		driver.findElement(By.xpath("//input[@ng-model='message.Subject']")).click();
		Action themeMessage = new Actions(driver).sendKeys(subject).build();
		themeMessage.perform();
		Thread.sleep(2000);
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
		driver.findElement(By.xpath("html/body/div[1]")).click();
		Action bodyMessage = new Actions(driver).sendKeys(body).build();
		bodyMessage.perform();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[@ng-click='save(message, true, false)']")).click();	// save druft message
		driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();		// close druft message
		driver.findElement(By.xpath("//a[@href='/drafts']")).click();								// open druft folder
		Thread.sleep(2000);
		List<WebElement> druftList = (List<WebElement>) driver.findElements(By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));
		for (WebElement wlmt : druftList) {
			if (wlmt.findElement(By.xpath("//span[@class = 'senders-name']")).getText().equals(sender)	// search email sender
					&& wlmt.findElement(By.xpath("//span[@class = 'subject-text ellipsis']")).getText().equals(subject)) { // search email subject
				wlmt.click();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
				if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(body)) { // search email body
					driver.switchTo().defaultContent();
					System.out.println("The druft has been found");
					return true;
				} else {
					driver.switchTo().defaultContent();
					driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();
				}
			}
		}
		System.out.println("The druft has not been found");
		return false;
	}

	public static boolean sendDruftAndCheck(String sender, String subject) throws InterruptedException {
		driver.findElement(By.xpath("//button[@class='pm_button primary mobileFull composer-btn-send btnSendMessage-btn-action']")).click();
		System.out.println("The druft has been sended");
		// поиск в отправленных:
		driver.findElement(By.xpath("//a[@href='/sent']")).click();
		Thread.sleep(2000);
		List<WebElement> sendList = (List<WebElement>)
				driver.findElements(By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));
		for (WebElement wlmtsnt : sendList) {
			if (wlmtsnt.findElement(By.xpath("//span[@class = 'senders-name']")).getText().equals(sender) // search email sender
					&& wlmtsnt.findElement(By.xpath("//span[@class = 'subject-text ellipsis']")).getText().equals(subject)) { // search email subject
				System.out.println("Now your email is in SENT folder");
				driver.close();
				return true;
			} else {
				driver.switchTo().defaultContent();
				driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();
			}
		}
		return false;
	}
}
