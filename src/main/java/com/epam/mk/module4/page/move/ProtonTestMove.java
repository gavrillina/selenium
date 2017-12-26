package com.epam.mk.module4.page.move;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.mk.module4.entity.Mail;
import com.epam.mk.module4.exception.CannotLoginException;
import com.epam.mk.module4.exception.DraftNotFoundException;

public class ProtonTestMove {
	private WebDriver driver;
	MoveItemPage moveItemPage;

	@BeforeTest
	private void initDriver() {
//		System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver_x64.exe");   
//		FirefoxProfile firefoxProfile = new FirefoxProfile(new File("C:\\Users\\Kazbek_Muslayev\\Desktop\\firefox_x64_portable\\profile_selenium"));
//		FirefoxOptions firefoxOptions = new FirefoxOptions();
//		firefoxOptions.setBinary("C:\\Users\\Kazbek_Muslayev\\Desktop\\firefox_x64_portable\\application\\firefox.exe");
//		firefoxOptions.setProfile(firefoxProfile);
//		driver = new FirefoxDriver(firefoxOptions);
//		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
		
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		ChromeOptions chromeOption = new ChromeOptions();
		chromeOption.setBinary("C:\\Users\\user\\Desktop\\chrome\\chrome.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(70, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void loginPageTest() throws CannotLoginException {
		moveItemPage = new MoveLoginPage(driver).openUrl().loginAction();
	}

	@Test(dependsOnMethods = { "loginPageTest" }, dataProvider = "myDetails")
	public void moveItemPageTest(Mail mail) throws DraftNotFoundException, InterruptedException {
		moveItemPage.moveToSpam(mail);
	}

	@AfterTest(enabled = false)
	public void closeDriver() {
		driver.quit();
	}

	@DataProvider
	public Object[][] myDetails() {
		return new Object[][] { { new Mail("mailer-daemon@corp.mail.ru", "", "") }, };
	}
}
