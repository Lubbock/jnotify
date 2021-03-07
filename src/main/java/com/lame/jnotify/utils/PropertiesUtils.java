package com.lame.jnotify.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Object.class.getResourceAsStream("/jnotify.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBasePackage() {
        return properties.getProperty("repo.base.package");
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }

}
