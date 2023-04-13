package com.example.pp.core;

import lombok.Getter;

public enum GameState {
    NOT_INITIALIZED("not_initialized"),
    INITIALIZED("initialized"),
    WAITING_FOR_PLAYERS_TO_JOIN("waiting_for_players_to_join"),
    ALL_PLAYER_JOINED("all_player_joined"),
    START("ready_to_start"),
    WAITING_FOR_PLAYER_MOVE("waiting_for_player_move"),
    MAKE_MOVE("make_move"),
    VALIDATE_GAME("validate_game"),
    FINISH("finish"),
    AWARDS("award_player"),
    TERMINATE("terminate");


    @Getter
    private String value;

    GameState(String value) {
        this.value = value;
    }

}