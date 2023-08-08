package com.javarush.island.zaveyboroda.gamefield;

import com.javarush.island.zaveyboroda.entities.Nature;

import java.util.HashMap;
import java.util.HashSet;

public class Island {
    private Island island;
    private Cell[][] cells;

    public static class Cell {
        HashMap<String, HashSet<Nature>> naturesOnCellAmount;
    }
}
