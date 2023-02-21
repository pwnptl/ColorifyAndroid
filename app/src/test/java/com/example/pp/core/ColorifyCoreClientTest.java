package com.example.pp.core;

import org.junit.Test;

public class ColorifyCoreClientTest {

    @Test
    public void checkGetSync() {
        ColorifyCoreClient colorifyCoreClient = new ColorifyCoreClient();
        String host = "localhost";
        String url = "http://" + host + ":8080/hello";
        System.out.println("Sending Request to "+ url);
        String output = colorifyCoreClient.getSync(url,"" );
        System.out.println("Result = " + output);
    }

    @Test
    public void checkGetSync2() {
        ColorifyCoreClient colorifyCoreClient = new ColorifyCoreClient();
        String urlpath = "/hello";
        System.out.println("Sending Request to "+ urlpath);
        String output = colorifyCoreClient.getSync(urlpath,"" );
        System.out.println("Result = " + output);
    }
}