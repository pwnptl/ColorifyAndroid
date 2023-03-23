package com.example.pp.core.network;

import com.example.pp.core.utility.ObjectJsonConverter;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payload {

    private final String messageType;
    private final String messageData;

    public Payload(String messageType, Object obj) {
        String messageData = ObjectJsonConverter.toJSON(obj);
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public static Payload fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Payload.class);
    }

    public String asJson() {
        return ObjectJsonConverter.toJSON(this);
    }
}