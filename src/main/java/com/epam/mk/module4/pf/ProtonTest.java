package com.epam.mk.module4.pf;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ProtonTest {
	private WebDriver driver;

	CreateDruftPage createMailPage;
	
	
	@BeforeClass(description = "Start browser")
	private void initBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
	//	ChromeOptions chromeOption = new ChromeOptions();
	//	chromeOption.setBinary("C:\\Users\\muslayev\\Desktop\\chrome\\chrome.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}


    @Test(enabled=true)
    public void LoginPageTest()  {
    	Assert.assertNotNull(createMailPage = new LoginPage(driver).openUrl().createDruftPage());
    }

	@Test(description = "Proton Mail Test", dependsOnMethods = {"LoginPageTest"})
	public void testProton() throws InterruptedException {
		SendDruftPage sendMailPage = createMailPage.openPage();
		Assert.assertEquals(sendMailPage.sendDruft(), "Now your email is in SENT folder");
	}

	@AfterClass(description = "Close browser")
	public void kill() {
	//	driver.close();
	//	driver.quit();
	}
}
