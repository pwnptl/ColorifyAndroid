package com.example.pp.core.models.typeAdapters;

import com.example.pp.core.models.Cell;

import java.util.HashMap;
import java.util.Map;

public class TypeAdapters {
    public static  Map<Class, Object> getAdapters() {
        Map<Class, Object> typeAdapters = new HashMap<>();
        typeAdapters.put(Cell.class, new BoardCellTypeAdapter());
//        typeAdapters.put(ScoreTracker.class, new ScoreTrackerTypeAdapter());
        return typeAdapters;
    }
}
