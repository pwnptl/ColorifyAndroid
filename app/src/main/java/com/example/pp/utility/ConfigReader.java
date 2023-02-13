package com.example.pp.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {
    private ConfigReader() {
    }

    static Properties props;

    static {
        File configFile = new File("config.properties");

        try {
            FileReader reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.print("file does not exist");
            // file does not exist
        } catch (IOException ex) {
            // I/O error
        }
    }

    public static String getProperty(final String propName) {
        return props.getProperty(propName);
    }
}
