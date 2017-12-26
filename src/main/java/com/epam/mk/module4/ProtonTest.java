package com.epam.mk.module4;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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

	@BeforeTest
	private void initDriver()   {
		try {
			driver = new RemoteWebDriver(new URL("http://epkzkarw0446:4444/wd/hub"), DesiredCapabilities.chrome());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();	

		// System.setProperty("webdriver.gecko.driver",
		// "src/main/resources/geckodriver_x64.exe");
		// FirefoxProfile firefoxProfile = new FirefoxProfile(new
		// File("C:\\Users\\Kazbek_Muslayev\\Desktop\\firefox_x64_portable\\profile_selenium"));
		// FirefoxOptions firefoxOptions = new FirefoxOptions();
		// firefoxOptions.setBinary("C:\\Users\\Kazbek_Muslayev\\Desktop\\firefox_x64_portable\\application\\firefox.exe");
		// firefoxOptions.setProfile(firefoxProfile);
		// driver = new FirefoxDriver(firefoxOptions);
		// driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.manage().window().maximize();
		
		
		
		
		// System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
//		// ChromeOptions chromeOption = new ChromeOptions();
//		// chromeOption.setBinary("C:\\Users\\user\\Desktop\\chrome\\chrome.exe");
//		driver = new ChromeDriver();
//		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
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
		Assert.assertEquals(sendDraftPage.sendDraft(mail), "Now your email is in SENT folder");
	}

	@AfterTest(enabled = true)
	public void closeDriver() {
		driver.quit();
	}

	@DataProvider
	public Object[][] myDetails() {
		return new Object[][] { { new Mail("test@mail.ru", "kazbek", "modul4.3") }, };
	}

}
