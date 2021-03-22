package com.lame.jnotify.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    Properties properties;

    public PropertiesUtils(Properties properties) {
        this.properties = properties;
    }
    public static PropertiesUtils initConfig(String propPath) {
        Properties properties = new Properties();
        PropertiesUtils p = new PropertiesUtils(properties);
        try (FileInputStream fis = new FileInputStream(propPath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public String getBasePackage() {
        return properties.getProperty("repo.base.package");
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

}
