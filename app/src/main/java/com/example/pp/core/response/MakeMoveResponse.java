package com.example.pp.core.response;

import com.example.pp.core.GameState;
import com.example.pp.core.models.Board;
import com.example.pp.core.models.ColorifyPalette;
import com.example.pp.core.models.ScoreTracker;

import java.util.ArrayList;

import lombok.Getter;

/**
 * MakeMove and GetGame Responses are both responses are essentially same.
 */
@Getter
public class MakeMoveResponse extends GetGameResponse {
    public MakeMoveResponse(String requesterPlayerId, String gameId, int totalPossiblePlayerCount, ArrayList<String> currentPlayerIds, String currentPlayerChance, int moveCount, GameState gameState, Board board, ColorifyPalette palette, ScoreTracker scoreTracker) {
        super(requesterPlayerId, gameId, totalPossiblePlayerCount, currentPlayerIds, currentPlayerChance, moveCount, gameState, board, palette, scoreTracker);
    }

}
