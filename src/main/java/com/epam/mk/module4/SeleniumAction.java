package com.epam.mk.module4;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class SeleniumAction {

    public static void main(String[] args) throws Exception {
        createDruftAndCheck(
                PropertiesLoader.getInfo("URL"),
                PropertiesLoader.getInfo("USERNAME"),
                PropertiesLoader.getInfo("PASSWORD"),
                PropertiesLoader.getInfo("SENDER"),
                PropertiesLoader.getInfo("SUBJECT"),
                PropertiesLoader.getInfo("BODY")
        );
        //   searchDruftAndSend();
    }

    public static String loginMail(String url, String username, String password) {
        String welcomeText;
        WebDriver driver = new SeleniumDriver().chromeDrv();
        driver.get(url);
        driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@id='login_btn']")).click();
        welcomeText = driver.findElement(By.xpath("//div[@ng-if='showWelcome']/header")).getText();
        driver.close();
        return welcomeText;
    }

    public static boolean createDruftAndCheck(String url, String username, String password, String sender, String subject, String body)
            throws InterruptedException {
        WebDriver driver = new SeleniumDriver().chromeDrv();
        driver.get(url);
        driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@id='login_btn']")).click();
        driver.findElement(By.xpath("//button[@class='compose pm_button sidebar-btn-compose']")).click();
        driver.findElement(By.xpath("//input[@id='autocomplete']")).sendKeys(sender);
        driver.findElement(By.xpath("//input[@ng-model='message.Subject']")).click();
        Action themeMessage = new Actions(driver).sendKeys(subject).build();
        themeMessage.perform();
        Thread.sleep(2000);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
        driver.findElement(By.xpath("html/body/div[1]")).click();
        Action bodyMessage = new Actions(driver).sendKeys(body).build();
        bodyMessage.perform();
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@ng-click='save(message, true, false)']")).click();    // save druft message
        driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();        // close druft message
        //check
        driver.findElement(By.xpath("//a[@href='/drafts']")).click();                                // open druft folder
        Thread.sleep(2000);
        List<WebElement> druftList = (List<WebElement>)
                driver.findElements(By.xpath("//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));
        for (WebElement wlmt : druftList) {
            if (wlmt.findElement(By.xpath("//span[@class = 'senders-name']")).getText().equals(sender) // search email sender
                    && wlmt.findElement(By.xpath("//span[@class = 'subject-text ellipsis']")).getText().equals(subject)) {  // search email subject
                wlmt.click();
                driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(body)) {   // search email body
                    driver.switchTo().defaultContent();
                    System.out.println("The druft has been founded");
                    return true;
                } else {
                    driver.switchTo().defaultContent();
                    driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();
                }
            }
        }
        System.out.println("The druft has not been founded");
        return false;
        //driver.close();
    }

    public static void searchDruftAndSend() {
        WebDriver driver = new SeleniumDriver().chromeDrv();
        driver.get("https://protonmail.com");
        driver.findElement(By.xpath("//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("hashmap@protonmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456qw");
        driver.findElement(By.xpath("//button[@id='login_btn']")).click();
        driver.findElement(By.xpath("//a[@href='/drafts']")).click();
        List<WebElement> druftList = (List<WebElement>) driver.findElements(
                By.xpath("//div[@ng-repeat = 'conversation in conversations track by conversation.ID']"));
        for (WebElement wlmt : druftList) {
            if (wlmt.findElement(By.xpath("//span[@class = 'senders-name']")).getText().equals("test@test.com")
                    && wlmt.findElement(By.xpath("//span[@class = 'subject-text ellipsis']")).getText().equals("my subject")) {
                wlmt.click();
                driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class = 'squireIframe']")));
                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals("hello everybody")) {
                    driver.switchTo().defaultContent();
                    // driver.findElement(By.xpath("//button[@data-message='message']")).click();
                } else
                    driver.switchTo().defaultContent();
                driver.findElement(By.xpath("//button[@ng-click='openCloseModal(message)']")).click();
            }
        }
        //driver.close();
    }
}
