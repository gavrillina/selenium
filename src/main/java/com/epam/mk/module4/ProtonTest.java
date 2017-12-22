package com.epam.mk.module4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProtonTest {
	private WebDriver driver;
	CreateDruftPage createDruftPage;
	SendDruftPage sendDruftPage;

	@BeforeClass
	private void initDriver() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		//ChromeOptions chromeOption = new ChromeOptions();
		//chromeOption.setBinary("C:\\Users\\user\\Desktop\\chrome\\chrome.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void loginPageTest() {
		Assert.assertNotNull(createDruftPage = new LoginPage(driver).openUrl().loginAction());
	}

	@Test(dependsOnMethods = { "loginPageTest" })
	public void createDruftPageTest() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		Assert.assertNotNull(sendDruftPage = createDruftPage.createDruft().searchDruft());
		// Assert.assertNotNull(sendDruftPage = createDruftPage.searchDruft()); // ЧТОБЫ ПРОПУСТИТЬ СОЗДАНИЕ ПИСЬМА И НАЧАТЬ С ПОИСКА В ЧЕРНОВИКАХ
	}

	@Test(dependsOnMethods = { "createDruftPageTest" })
	public void sendDruftPageTest() throws InterruptedException {
		Assert.assertEquals(sendDruftPage.sendAction(), "Now your email is in SENT folder");
	}

	@AfterTest(enabled = true)
	public void closeDriver() {
		driver.quit();
	}
}
