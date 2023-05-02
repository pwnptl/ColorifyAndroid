package com.example.pp.core.response;

import com.example.pp.core.GameState;
import com.example.pp.core.models.Board;
import com.example.pp.core.models.ColorifyPalette;
import com.example.pp.core.models.ScoreTracker;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetGameResponse extends Response {

    private final String requesterPlayerId;
    private final String gameId;
    private final int totalPossiblePlayerCount;
    private final ArrayList<String> currentPlayerIds;

    private final GameState gameState;

    private final Board board;
    private final ColorifyPalette palette;

    private final ScoreTracker scoreTracker;

}
