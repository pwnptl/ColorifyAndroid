package com.example.pp.core;

import com.example.pp.utility.ConfigReader;

import java.util.logging.Logger;

public abstract class BaseNetwork {
    protected static Logger logger = Logger.getLogger("Network");

    protected String host = ConfigReader.getProperty("server.ip");
    protected int port = Integer.parseInt(ConfigReader.getProperty("server.port"));

    static BaseNetwork instance;

    // private constructor.
    protected BaseNetwork() {
    }

    public static BaseNetwork getInstance() {
        String coreClient = ConfigReader.getProperty("server.coreClient");
        if (instance == null)
            if ("URLConnectionBased".equals(coreClient))
                instance = new ColorifyCoreClient();
            else
                throw new IllegalArgumentException("No URL Connector for :" + coreClient);
        return instance;
    }

    public abstract void setup();

    public abstract String getSync(String targetPath, String urlParameters);

    public abstract String getAsync();

    public abstract String post();

    public abstract String delete();

}
