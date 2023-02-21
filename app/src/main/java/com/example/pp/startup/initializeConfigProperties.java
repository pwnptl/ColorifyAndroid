package com.example.pp.startup;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.example.pp.utility.ConfigReader;

import java.util.List;

public class initializeConfigProperties implements Initializer<ConfigReader>{
    @NonNull
    @Override
    public ConfigReader create(@NonNull Context context) {

        return new ConfigReader(context);
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return null;
    }
}
