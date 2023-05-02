package com.example.pp.core.models;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class ScoreTracker {
    protected Map<String, Score> playerIdToScoreMap;
    protected int totalCells;
}
