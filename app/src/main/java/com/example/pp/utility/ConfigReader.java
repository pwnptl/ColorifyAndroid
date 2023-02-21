package com.example.pp.utility;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public final class ConfigReader {
    static Properties props;

    public ConfigReader(Context context) {

//        try {
//
//            FileReader reader = new FileReader(configFile);
//            props = new Properties();
//            props.load(reader);
//            System.out.println("hahaha" + props.stringPropertyNames());
//            reader.close();
//        } catch (FileNotFoundException ex) {
//            System.out.println("file does not exist at locations : " + System.getProperty("user.dir"));
//            System.out.println("paths" + Paths.get(""));
//        } catch (IOException ex) {
//            // I/O error
//        }
    }

    public static String getProperty(final String propName) {
        return props.getProperty(propName);
    }

    public static void init(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            props = new Properties();
            props.load(inputStream);
            System.out.println( "okay " + Arrays.toString(props.stringPropertyNames().toArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
