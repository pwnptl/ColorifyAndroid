package com.example.pp.core.response;

import com.example.pp.core.GameState;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinGameResponse extends Response {
    // todo : add reason type in Response in case of failures to show on frontend.
    private final String gameId;
    private final boolean joined;
    private ArrayList<String> joinedPlayers;
    private GameState gameState;
}
