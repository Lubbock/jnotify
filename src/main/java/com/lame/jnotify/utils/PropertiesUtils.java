package com.lame.jnotify.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtils {
    static Properties properties;

    static {
        properties = new Properties();
        try( FileInputStream fis = new FileInputStream("./jnotify.properties")) {
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
