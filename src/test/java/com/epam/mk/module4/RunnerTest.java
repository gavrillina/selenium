package com.epam.mk.module4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunnerTest {

    WebDriver driver;
    Boolean myDynamicElement;

    @BeforeClass
    private void openBrauser(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://protonmail.com/");
    }

    @Test
    private void logIn(){
        driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='username']")).sendKeys("automationTest@protonmail.com");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("test123456");
        driver.findElement(By.xpath("//*[@id='login_btn']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String welcomText = driver.findElement(By.xpath("//*[@id='pm_latest']/header")).getText();
        Assert.assertEquals("Добро пожаловать", welcomText);
    }

    @Test(dataProvider="testDataForMail", dependsOnMethods ={"logIn"} )
    private void createNewMail(String email, String subject, String textContent) throws InterruptedException {
        driver.findElement(By.cssSelector(".compose.pm_button.sidebar-btn-compose")).click();
        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBe(By.cssSelector(".subject.composerHeader-subject"),"New message"));
        driver.findElement(By.cssSelector("#autocomplete")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id='uid1']/div[2]/div[5]/input")).sendKeys(subject);

        WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
        driver.switchTo().frame(frame);

        driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).click();

        Actions make  = new Actions(driver);
        Action kbEvents = make.sendKeys(textContent).build();
        kbEvents.perform();
        Thread.sleep(2000);


        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//*[@data-original-title = 'Закрыть']")).click();


    }
    @DataProvider
    public Object[][] testDataForMail(){
        return new Object[][]{
                {"aibar.abilchanov@mail.ru", "From Aibar", "Test automation engineers in da house!!! "}
        };
    }

    @Test(dataProvider = "testDataForMail" , dependsOnMethods = {"createNewMail"})
    private void checkingDraftPresence(String email, String subject, String textContent) throws InterruptedException {
        driver.findElement(By.xpath(".//span[text() = 'Черновики']")).click();
        List<WebElement> list = (List<WebElement>) driver.findElements(By.xpath(".//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));

        for (WebElement webElement : list) {

            if (webElement.findElement(By.xpath("//*[@class = 'senders-name']")).getText().equals(email) &&
                    webElement.findElement(By.xpath("//*[@class = 'subject-text ellipsis']")).getText().equals(subject)){
                webElement.click();
                WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
                driver.switchTo().frame(frame);

                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(textContent)){
                    driver.switchTo().defaultContent();
                    driver.findElement(By.xpath(".//*[text()='Отправить']")).click();
                }

            }
        }

    }

    }
