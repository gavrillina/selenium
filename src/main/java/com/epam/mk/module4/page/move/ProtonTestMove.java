package com.epam.mk.module4.page.move;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
		System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");   
		FirefoxOptions firefoxOption = new FirefoxOptions();
		firefoxOption.setBinary("C:\\Users\\Kazbek_Muslayev\\Desktop\\firefox_x64_portable\\application\\firefox.exe");
		driver = new FirefoxDriver(firefoxOption);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
