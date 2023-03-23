package com.example.pp.core.response;

import com.example.pp.core.game.PlayerType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPlayerResponse extends Response {

    protected String playerId;
    protected String name;
    protected PlayerType type;
}