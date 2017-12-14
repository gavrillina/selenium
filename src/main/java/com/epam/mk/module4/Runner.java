package com.epam.mk.module4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Runner {
	public static void main(String[] args) {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://protonmail.com");
		driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("automationTest@protonmail.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("test123456");
		driver.findElement(By.xpath("//button[@id='login_btn']")).click();
		driver.findElement(By.xpath("//*[@id='pm_sidebar']/button")).click();
		driver.findElement(By.xpath("//input[@id='autocomplete']")).sendKeys("test@mail.ru");
	//	driver.findElement(By.xpath("//input[@placeholder='Тема']")).sendKeys("Hello my friend");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='squireIframe']")));
		driver.findElement(By.xpath("html/body/div[1]/")).sendKeys("testestrsdfsdf");
	}
}
