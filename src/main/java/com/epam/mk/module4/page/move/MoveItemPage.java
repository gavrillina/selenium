package com.epam.mk.module4.page.move;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.epam.mk.module4.entity.Mail;
import com.epam.mk.module4.exception.DraftNotFoundException;
import com.epam.mk.module4.page.AbstractPage;

public class MoveItemPage extends AbstractPage {

	public MoveItemPage(WebDriver driver) {
		super(driver);
	}

	private By openKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]");
	}

	private By senderKost(int i) {
		return By.xpath("html/body/div[2]/div[1]/div/div[1]/section/div[" + i + "]/div[2]/div[2]/span/span");
	}

	@FindBy(xpath = "//section[@id='pm_sidebar']/ul/li[6]/a")
	private WebElement spamFolderUrl;

	@FindBy(xpath = "//div[@ng-repeat = 'conversation in conversations track by conversation.ID']")
	private List<WebElement> InboxList;

	@FindBy(xpath = ".//*[@id='pm_placeholder']/h2")
	private WebElement messageTest;
	
	public MoveItemPage moveToSpam(Mail mail) throws DraftNotFoundException, InterruptedException {
		waitForElementVisible(InboxList.get(0));
		for (int i = 1; i <= InboxList.size(); i++) {
			if (driver.findElement(senderKost(i)).getText().equals(mail.getSender())) {
				Actions act = new Actions(driver);
				act.clickAndHold(driver.findElement(openKost(i)));
				act.moveToElement(driver.findElement(openKost(i))).release(spamFolderUrl).build().perform();
				
				
				
				
			}
		}
		throw new DraftNotFoundException("Can't to move Inbox item");
	}
}
