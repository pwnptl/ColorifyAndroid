package com.example.pp.utility;

import org.junit.Test;

public class ConfigReaderTest  {

    @Test
    public void readConfig()
    {
        assert ConfigReader.getProperty("username") != null;
    }
}