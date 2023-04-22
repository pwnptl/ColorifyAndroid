package com.example.pp.core.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {

    private final ArrayList<ArrayList<Cell>> grid;
    private final int rows;
    private final int cols;
    private final int uniqueColors;


    public Cell getCell(final int r, final int c) {
        return grid.get(r).get(c);
    }
}
