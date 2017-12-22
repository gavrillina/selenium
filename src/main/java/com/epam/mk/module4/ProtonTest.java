package com.epam.mk.module4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.mk.module4.entity.Mail;
import com.epam.mk.module4.exception.CannotLoginException;
import com.epam.mk.module4.exception.DraftNotFoundException;
import com.epam.mk.module4.page.CreateDraftPage;
import com.epam.mk.module4.page.LoginPage;
import com.epam.mk.module4.page.SendDraftPage;

public class ProtonTest {
	private WebDriver driver;
	private CreateDraftPage createDraftPage;
	private SendDraftPage sendDraftPage;

	@DataProvider
	public Object[][] myDetails() {
		return new Object[][] { { new Mail("test@mail.ru", "my_subject", "hello everybody") }, };
	}

	@BeforeTest
	private void initDriver() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		// ChromeOptions chromeOption = new ChromeOptions();
		// chromeOption.setBinary("C:\\Users\\user\\Desktop\\chrome\\chrome.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void loginPageTest() throws CannotLoginException {
		createDraftPage = new LoginPage(driver).openUrl().loginAction();
	}

	@Test(dependsOnMethods = { "loginPageTest" }, dataProvider = "myDetails")
	public void createDraftPageTest(Mail mail) throws DraftNotFoundException {
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		sendDraftPage = createDraftPage.createDraft(mail).searchDraft(mail);
	}

	@Test(dependsOnMethods = { "createDraftPageTest" }, dataProvider = "myDetails", enabled = true)
	public void sendDraftPageTest(Mail mail) throws DraftNotFoundException {
		Assert.assertEquals(sendDraftPage.sendAction(mail), "Now your email is in SENT folder");
	}

	@AfterTest(enabled = true)
	public void closeDriver() {
		driver.quit();
	}
}
