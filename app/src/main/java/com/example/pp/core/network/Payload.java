package com.example.pp.core.network;

import com.example.pp.core.utility.ObjectJsonConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payload {

    private String messageType;
    private String messageData;

    public Payload(String messageType, Object obj) {
        String messageData = ObjectJsonConverter.toJSON(obj);
        this.messageType = messageType;
        this.messageData = messageData;
    }
}