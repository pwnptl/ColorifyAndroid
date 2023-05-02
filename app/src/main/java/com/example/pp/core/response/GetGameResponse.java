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

    protected final String requesterPlayerId;
    protected final String gameId;
    protected final int totalPossiblePlayerCount;
    protected final ArrayList<String> currentPlayerIds;

    protected final GameState gameState;

    protected final Board board;
    protected final ColorifyPalette palette;

    protected final ScoreTracker scoreTracker;

}
