package org.upfunda.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configreader {

    private Properties prop;

    public configreader() {
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("Config file not found!");
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}
