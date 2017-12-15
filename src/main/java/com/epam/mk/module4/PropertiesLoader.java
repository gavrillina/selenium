package com.epam.mk.module4;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private final static String INFO_PROPERTIES = "/info.properties";
    private static Properties properties = new Properties();
    private static InputStream inputStream;

    public static String getInfo(String key) {
        inputStream = PropertiesLoader.class.getResourceAsStream(INFO_PROPERTIES);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
