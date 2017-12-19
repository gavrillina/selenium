package com.epam.mk.module4.pf;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProtonTest {
	private WebDriver driver;

	@BeforeClass(description = "Start browser")
	private void initBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		ChromeOptions chromeOption = new ChromeOptions();
		chromeOption.setBinary("C:\\Users\\user\\Desktop\\chrome\\chrome.exe");
		driver = new ChromeDriver(chromeOption);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test(description = "Proton Mail Test")
	public void testProton() throws InterruptedException {
	//	CreateDruftPage createMailPage = new LoginPage(driver).openUrl().openPage();
		CreateDruftPage createMailPage = new LoginPage(driver).openUrl().openPage();
		SendDruftPage sendMailPage = createMailPage.openPage();
		Assert.assertEquals(sendMailPage.sendDruft(), "Now your email is in SENT folder");
	}

	@AfterClass(description = "Close browser")
	public void kill() {
		driver.close();
	}
}
