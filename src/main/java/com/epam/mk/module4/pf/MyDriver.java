package com.epam.mk.module4.pf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class MyDriver {
    protected WebDriver driver;

    protected MyDriver(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
