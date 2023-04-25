package com.example.pp.core.utility;

import com.example.pp.core.models.typeAdapters.TypeAdapters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ObjectJsonConverter {

    private static Gson gson;

    private static Gson getGson() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            for (Map.Entry<Class, Object> pair : TypeAdapters.getAdapters().entrySet()) {
                gsonBuilder.registerTypeHierarchyAdapter(pair.getKey(), pair.getValue());
            }
            gson = gsonBuilder.create();
        }
        return gson;
    }

    public static String toJSON(Object object) {
        return getGson().toJson(object);
    }


    public static boolean isJson(String message) {
        // https://stackoverflow.com/a/10174938/5800424
        try {
            new JSONObject(message);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(message);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static <T> Object fromJson(String json, Class<T> c) {
        return getGson().fromJson(json, c);
    }
}
