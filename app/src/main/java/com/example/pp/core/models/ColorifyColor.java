package com.example.pp.core.models;

import android.graphics.Color;

import lombok.Getter;

public enum ColorifyColor {
    CYAN(Color.CYAN),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    RED(Color.RED),
    MAGENTA(Color.MAGENTA);

    @Getter
    private final int color;

    ColorifyColor(int color) {
        this.color = color;
    }

    // todo: below method is only for IntegerCell type.
    public static ColorifyColor valueOf(int cellValue) {
        return values()[cellValue];
    }
}
