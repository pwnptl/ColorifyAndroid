package com.example.pp.core.models;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ColorifyPalette extends Palette {
    private final ArrayList<Cell> paletteCells;
}