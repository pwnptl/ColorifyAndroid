package com.example.pp.core.game;

import lombok.Getter;

@Getter
public enum PlayerType {
    HUMAN("human"),
    BOT("bot"),
    UNRECOGNISED("unrecognised");

    PlayerType(String human) {
    }
}
