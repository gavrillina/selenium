package com.epam.mk.module4;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SeleniumActionTest {

    @DataProvider
    public Object[][] loginMailProvider() {
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

    @Test(dataProvider = "loginMailProvider")
    public void loginMail(String url, String username, String password) {
        Assert.assertEquals(SeleniumAction.loginMail(url, username, password), "Добро пожаловать");
    }


    @Test(dataProvider = "loginMailProvider")
    public void createDruftAndCheck(String url, String username, String password, String sender, String subject, String body) throws InterruptedException {
        Assert.assertEquals(SeleniumAction.createDruftAndCheck(url, username, password, sender, subject, body), true);
    }
}
