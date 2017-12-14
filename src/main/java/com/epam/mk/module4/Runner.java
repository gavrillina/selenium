package com.epam.mk.module4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class Runner {
	public static void main(String[] args) {
		// createDruft();
		searchDruftAndSend();
	}

	public static void createDruft() {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://protonmail.com");
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("hashmap@protonmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456qw");
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		driver.findElement(By.xpath("//*[@id='pm_sidebar']/button")).click();
		driver.findElement(By.xpath("//input[@id='autocomplete']")).sendKeys("test@mail.ru");
		driver.findElement(By.xpath("//input[@ng-model='message.Subject']")).click();
		Action themeMessage = new Actions(driver).sendKeys("My Subject").build();
		themeMessage.perform();
		WebElement bodyMailFrame = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
		driver.switchTo().frame(bodyMailFrame);
		driver.findElement(By.xpath("html/body/div[1]")).click();
		Action bodyMessage = new Actions(driver).sendKeys("This is my body message").build();
		bodyMessage.perform();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[@class='composer-btn-save']")).click();
	}

	public static void searchDruftAndSend() {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://protonmail.com");
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("hashmap@protonmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456qw");
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		driver.findElement(By.xpath("//a[@href='/drafts']")).click();

	}
}
