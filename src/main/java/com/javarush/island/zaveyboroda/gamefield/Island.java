package com.javarush.island.zaveyboroda.gamefield;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.plants.PlantFeatures;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.repository.DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Island {
    private final int WIDTH = 5;
    private final int HEIGHT = 5;
    private final MainController mainController;
    private Island island;
    private final Cell[][] cells;

    public Island(MainController mainController) {
        this.mainController = mainController;
        cells = new Cell[WIDTH][HEIGHT];
    }

    private void initCellsAndNature() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                cells[i][j] = new Cell(i, j);
                Cell cell = cells[i][j];
                cell.createNature();
                initNatureOnCell(cell, mainController.getDataBase());
            }
        }

        HashMap<String, HashSet<? extends Nature>> map = cells[0][0].natureOnCell;

        map.forEach(((s, natures) -> System.out.println(s + " " + natures)));
    }

    public void startSimulation() {
        initCellsAndNature();


    }

    private <T extends Nature> void initNatureOnCell(Cell cell, DataBase db) {
        HashMap<String, HashSet<? extends Nature>> naturesOnCell = new HashMap<>();
        HashMap<String, Class<? extends Nature>> natureClassesMap = db.getNatureClassesMap();

        for (String key: natureClassesMap.keySet()) {
            Class<? extends Nature> natureClass = db.getNatureClassesMap().get(key);

            if (db.getNaturesConfigMap().get(key) == 0) {
                naturesOnCell.put(key, new HashSet<>());
            }

            HashSet<Nature> naturesSet = new HashSet<>();
            for (int i = 0; i < db.getNaturesConfigMap().get(key); i++){
                Nature natureObject = NatureFactory.createNature(natureClass, db, cell, false);

                naturesSet.add(natureObject);
                naturesOnCell.put(key, naturesSet);
            }
        }

        cell.setNatureOnCell(naturesOnCell);
    }

    public static class Cell {
        private int x;
        private int y;
        private HashMap<String, HashSet<? extends Nature>> natureOnCell;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public HashMap<String, HashSet<? extends Nature>> getNatureOnCell() {
            return natureOnCell;
        }

        public void setNatureOnCell(HashMap<String, HashSet<? extends Nature>> natureOnCell) {
            this.natureOnCell = natureOnCell;
        }

        private void createNature() {

        }
    }
}
