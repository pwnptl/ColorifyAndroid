package com.example.pp.core.utility;

import com.example.pp.core.messageHandler.MessageHandlerType;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjectJsonConverter {

    final static String MESSAGE_TYPE = "messageType";
    final static String MESSAGE_DATA = "messageData";

    public static String toJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    // todo: segregate payload creation in a separate Paylaod class file.
    public static String toJSONWithType(String messageType, Object obj) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(obj);
        JsonObject jsonObject = (JsonObject) jsonElement;

        Payload payload = new Payload(messageType, jsonObject);
        return toJSON(payload);
    }

    public static MessageHandlerType getMessageType(String message) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
            String type = jsonObject.getString(MESSAGE_TYPE);
            return MessageHandlerType.getValue(type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MessageHandlerType.UNKNOWN;
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

    private static class Payload {

        private String messageType;
        private JsonObject messageData;

        // todo : use lombok for below.
        public Payload(String messageType, JsonObject messageData){
            this.messageData = messageData;
            this.messageType = messageType;
        }

        public String getMessageType() {
            return messageType;
        }

        public JsonObject getMessageData() {
            return messageData;
        }
    }
}
