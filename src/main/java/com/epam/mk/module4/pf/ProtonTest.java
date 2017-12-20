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

	CreateDruftPage createDruftPage;
	SendDruftPage sendDruftPage;
	
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


    @Test()
    public void LoginPageTest()  {
    	Assert.assertNotNull(createDruftPage = new LoginPage(driver).openUrl().loginAction());
    }

    @Test(dependsOnMethods = {"LoginPageTest"})
    public void CreateDruftPageTest()  {
    	Assert.assertNotNull(sendDruftPage = createDruftPage.createAction());
    }
  
	@Test(dependsOnMethods = {"CreateDruftPageTest"})
	public void SendDruftPageTest() throws InterruptedException {
			Assert.assertEquals(sendDruftPage.sendAction(), "Now your email is in SENT folder");
	}

	@AfterClass(description = "Close browser")
	public void kill() {
		driver.close();
		driver.quit();
	}
}
