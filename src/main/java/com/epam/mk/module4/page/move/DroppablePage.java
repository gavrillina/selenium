package com.epam.mk.module4.page.move;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


import com.epam.mk.module4.page.AbstractPage;

public class DroppablePage extends AbstractPage {

	public DroppablePage(WebDriver driver) {
		super(driver);
	}
//a[@href='https://jqueryui.com/draggable/']
	
	@FindBy(xpath = "//a[@href='https://jqueryui.com/droppable/']")
	private WebElement droppablePage;

	@FindBy(xpath = "//iframe[@class = 'demo-frame']")
	private WebElement iFrame;

	@FindBy(xpath = "//div[@id='draggable']")
	private WebElement fromObject;
	
	@FindBy(xpath = "//div[@id='droppable']")
	private WebElement toObject;

	public DroppablePage action()  {
		driver.get("https://jqueryui.com");
		droppablePage.click();
		waitForElementVisible(iFrame);
		driver.switchTo().frame(iFrame);
		new Actions(driver).dragAndDrop(fromObject, toObject).build().perform();
		
		return this;
	}
}
