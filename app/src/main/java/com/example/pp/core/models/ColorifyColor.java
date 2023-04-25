package com.example.pp.core.models;

import android.graphics.Color;

public enum ColorifyColor {
    CYAN(Color.CYAN),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    RED(Color.RED),
    MAGENTA(Color.MAGENTA);

    private final int color;

    ColorifyColor(int color) {
        this.color = color;
    }
}
