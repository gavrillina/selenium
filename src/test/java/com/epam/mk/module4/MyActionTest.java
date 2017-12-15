package com.epam.mk.module4;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyActionTest {

	@DataProvider
	public Object[][] loginMailProvider() {
		return new Object[][] { { "https://protonmail.com", "hashmap", "123456qw" }, };
	}

	@Test(dataProvider = "loginMailProvider")
	public void loginMail(String url, String username, String password) {
		Assert.assertEquals(MyAction.loginMail(url, username, password), "Добро пожаловать");
	}
}
