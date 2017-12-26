package com.epam.mk.module4.page.move;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;

public class JqueryuiTest {
	private WebDriver driver;

	@BeforeTest
	private void initDriver() {

		System.setProperty("webdriver.chrome.driver", "src/main/resources/webdrivers/chromedriver_x86.exe");
		ChromeOptions chromeOption = new ChromeOptions();
		chromeOption.setBinary("C:\\Users\\user\\Desktop\\chrome\\chrome.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(70, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void droppableTest() {
		DroppablePage moveItemPage = new DroppablePage(driver);
		moveItemPage.action();
	}

	@AfterTest(enabled = false)
	public void closeDriver() {
		driver.quit();
	}

}
