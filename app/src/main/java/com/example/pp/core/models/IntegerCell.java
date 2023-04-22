package com.example.pp.core.models;

public class IntegerCell implements Cell {
    private int value;

    public IntegerCell(final int cell) {
        this.value = cell;
    }

    @Override
    public void setCell(int cell) {
        this.value = cell;
    }

    @Override
    public int getCell() {
        return value;
    }
}
