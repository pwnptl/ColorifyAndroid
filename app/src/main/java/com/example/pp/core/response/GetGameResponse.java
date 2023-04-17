package com.example.pp.core.response;

import com.example.pp.core.model.GameDataResponse;

import lombok.AllArgsConstructor;
import lombok.Setter;


@Setter
@AllArgsConstructor
public class GetGameResponse extends Response {

    private final String playerId;
    private final GameDataResponse gameDataResponse;

}
