package com.epam.mk.module4;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyActionTest {
	
	
	@DataProvider
	public Object[][] loginDetails() {
		return new Object[][] { { 35.5, 0.5 }, };
}

	@Test()
	public void loginMail() {
		Assert.assertEquals(MyAction.loginMail(), "Добро пожаловать");
}
}
