package com.epam.mk.module4.pf;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    @Test(description = "Amazon search test")
    public void amazonSearchTest() throws InterruptedException {
        CreateDruftPage createMailPage = new LoginPage(driver).open().openPage();
        SendDruftPage sendMailPage = createMailPage.openPage();
        
}
}
