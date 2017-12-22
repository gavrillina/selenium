package com.epam.mk.module4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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

	@DataProvider
	public Object[][] myDetails() {
		return new Object[][] { { new Mail("test@mail.ru", "my_subject", "hello everybody") }, };
}

	@Test
	public void loginPageTest() {
		Assert.assertNotNull(createDruftPage = new LoginPage(driver).openUrl().loginAction());
	}

	@Test(dependsOnMethods = { "loginPageTest" }, dataProvider = "myDetails")
	public void createDruftPageTest(Mail mail) {
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Assert.assertNotNull(sendDruftPage = createDruftPage.createDruft(mail).searchDruft(mail));
	}


	@Test(dependsOnMethods = { "createDruftPageTest" }, dataProvider = "myDetails")
	public void sendDruftPageTest(Mail mail) throws InterruptedException {
		Assert.assertEquals(sendDruftPage.sendAction(mail), "Now your email is in SENT folder");
	}

	@AfterTest(enabled = true)
	public void closeDriver() {
		driver.quit();
	}
}
