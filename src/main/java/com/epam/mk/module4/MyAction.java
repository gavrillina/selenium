package com.epam.mk.module4;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class MyAction {
	
	public static void main(String[] args) {
		//createDruft();
		//searchDruftAndSend();
		System.out.println(loginMail());
		
	}

	public static String loginMail() {
		String welcomeText;
		WebDriver driver = new SeleniumDriver().chromeDrv();
		driver.get("https://protonmail.com");
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("hashmap@protonmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456qw");
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		welcomeText = driver.findElement(By.xpath("//div[@id='pm_latest']/header")).getText();
		driver.close();
		return welcomeText;
	}

	public static void createDruft() {
		WebDriver driver = new SeleniumDriver().chromeDrv();
		driver.get("https://protonmail.com");
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("hashmap@protonmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456qw");
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		driver.findElement(By.xpath("//button[@class='compose pm_button sidebar-btn-compose']")).click();	
		driver.findElement(By.xpath("//input[@id='autocomplete']")).sendKeys("test@mail.ru");
		driver.findElement(By.xpath("//input[@ng-model='message.Subject']")).click();
		Action themeMessage = new Actions(driver).sendKeys("My Subject").build();
		themeMessage.perform();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
		driver.findElement(By.xpath("html/body/div[1]")).click();
		Action bodyMessage = new Actions(driver).sendKeys("This is my body message").build();
		bodyMessage.perform();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[@ng-click='save(message, true, false)']")).click();
		driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();
		driver.close();
	}

	public static void searchDruftAndSend() {
		WebDriver driver = new SeleniumDriver().chromeDrv();
		driver.get("https://protonmail.com");
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("hashmap@protonmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456qw");
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		driver.findElement(By.xpath("//a[@href='/drafts']")).click();
		List<WebElement> druftList = (List<WebElement>) driver.findElements(By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));
		for (WebElement wlmt : druftList) {
			if (wlmt.findElement(By.xpath("//*[@class = 'senders-name']")).getText().equals("test@mail.ru") 
					&& wlmt.findElement(By.xpath("//*[@class = 'subject-text ellipsis']")).getText().equals("My Subject")) {
				wlmt.click();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
				if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals("sdfsdfs")) {
					driver.switchTo().defaultContent();
					driver.findElement(By.xpath("//button[@data-message='message']")).click();
				} else
					driver.switchTo().defaultContent();
					driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();
			}
		}
		//driver.close();
	}
}
