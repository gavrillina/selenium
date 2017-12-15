package com.epam.mk.module4;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SeleniumActionTest {

    @DataProvider
    public Object[][] infoProvider() {
        return new Object[][]{{
                PropertiesLoader.getInfo("URL"),
                PropertiesLoader.getInfo("USERNAME"),
                PropertiesLoader.getInfo("PASSWORD"),
                PropertiesLoader.getInfo("SENDER"),
                PropertiesLoader.getInfo("SUBJECT"),
                PropertiesLoader.getInfo("BODY")
        },
        };
    }

    @Test(dataProvider = "infoProvider", enabled=true)
    public void loginMail(String url, String username, String password, String sender, String subject, String body) {
        Assert.assertEquals(SeleniumAction.loginMail(url, username, password), "Добро пожаловать");
    }


    @Test(dataProvider = "infoProvider", enabled=true, dependsOnMethods = {"loginMail"})
    public void createDruftAndCheck(String url, String username, String password, String sender, String subject, String body) throws InterruptedException {
        Assert.assertEquals(SeleniumAction.createDruftAndCheck(url, username, password, sender, subject, body), true);
    }

    @Test(dataProvider = "infoProvider", enabled=true, dependsOnMethods = {"createDruftAndCheck"} )
    public void sendDruftAndCheck(String url, String username, String password, String sender, String subject, String body) throws InterruptedException {
        Assert.assertEquals(SeleniumAction.sendDruftAndCheck(url, username, password, sender, subject, body), true);
    }
}
