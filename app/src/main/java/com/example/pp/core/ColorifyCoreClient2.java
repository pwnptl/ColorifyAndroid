package com.example.pp.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class ColorifyCoreClient2 extends BaseNetwork {

    protected ColorifyCoreClient2(){}

    @Override
    public void setup() {

    }

    @Override
    public String getSync(String targetPath, String urlParameters) {
        // todo add urlParameters
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL("https", host, port, targetPath);
            logger.log(Level.INFO, "Calling URL : " + url);
            URLConnection uc = url.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            uc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                stringBuilder.append(inputLine);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Override
    public String getAsync() {
        return null;
    }

    @Override
    public String post() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }
}
