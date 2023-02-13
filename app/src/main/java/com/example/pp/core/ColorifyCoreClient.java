package com.example.pp.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ColorifyCoreClient extends BaseNetwork {

    protected ColorifyCoreClient(){}

    @Override
    public void setup() {

    }

    @Override
    public String getSync(final String targetURL, final String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT; Windows NT 10.0; en-IN) WindowsPowerShell/5.1.22621.963");
//            connection.setRequestProperty("host","localhost:8080");
//            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            connection.setRequestProperty("Content-Length",
//                    Integer.toString(urlParameters.getBytes().length));
//            connection.setRequestProperty("Content-Language", "en-US");

//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//            Send request
//            DataOutputStream wr = new DataOutputStream(
//                    connection.getOutputStream());
////            wr.writeBytes(urlParameters);
//            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            System.out.println("response " + connection.getResponseMessage());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

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
