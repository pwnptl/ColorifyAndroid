package com.example.pp.core.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Player {

    protected String playerId;
    protected String name;
    protected PlayerType type;
}
