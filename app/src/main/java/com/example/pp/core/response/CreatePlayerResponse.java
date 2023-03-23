package com.example.pp.core.response;

import com.example.pp.core.utility.ObjectJsonConverter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePlayerResponse extends Response {
    private String playerId;

    public static CreatePlayerResponse fromJson(String json) {
        return (CreatePlayerResponse) ObjectJsonConverter.fromJson(json, CreatePlayerResponse.class);
    }
}
