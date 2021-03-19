package com.lame.jnotify.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    static Properties properties;


    public static void initConfig(String propPath) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propPath)) {
            properties.load(fis);
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
