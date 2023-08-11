package com.javarush.island.zaveyboroda.gamefield;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.repository.DataBase;

import java.util.*;

public class Island {
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    private final MainController mainController;
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

        HashMap<String, HashSet<Nature>> map = cells[0][0].natureOnCell;

        map.forEach(((s, natures) -> System.out.println(s + " " + natures.size())));
    }

    public void startSimulation() {
        initCellsAndNature();


        int i = 0;
        System.out.println("Day " + (i+1));
        System.out.println();
        List<AnimalFeatures> animalSetList = updateAnimalSetList();
        System.out.println(animalSetList.size());

        for (AnimalFeatures animal : animalSetList) {
//            animal.getCurrentLocation().getNatureOnCell().forEach((s, natures) -> System.out.println(s + " " + natures.size()));
//            System.out.println();
            animal.move(mainController, cells);
//            System.out.println();
//            animal.getCurrentLocation().getNatureOnCell().forEach((s, natures) -> System.out.println(s + " " + natures.size()));
        }

        System.out.println(updateAnimalSetList().size());
    }

    private List<AnimalFeatures> updateAnimalSetList() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .flatMap(cell -> cell.getNatureOnCell().values().stream())
                .flatMap(Collection::stream)
                .filter(nature -> nature instanceof AnimalFeatures)
                .map(animal -> (AnimalFeatures) animal)
                .toList();
    }

    private void initNatureOnCell(Cell cell, DataBase db) {
        HashMap<String, HashSet<Nature>> naturesOnCell = new HashMap<>();
        HashMap<String, Class<Nature>> natureClassesMap = db.getNatureClassesMap();

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

    public class Cell {
        private final int x;
        private final int y;
        private HashMap<String, HashSet<Nature>> natureOnCell;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public HashMap<String, HashSet<Nature>> getNatureOnCell() {
            return natureOnCell;
        }

        public void setNatureOnCell(HashMap<String, HashSet<Nature>> natureOnCell) {
            this.natureOnCell = natureOnCell;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cell cell)) return false;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        private void createNature() {

        }

        public boolean tryAddNature(Nature nature) {
            if (natureOnCell.get(nature.getClass().getSimpleName()).size() < mainController.getDataBase()
                    .getConstantNaturesFeaturesMap()
                    .get(nature.getClass().getSimpleName())
                    .getMAX_AMOUNT_ON_CELL()) {
                return natureOnCell.get(nature.getClass().getSimpleName()).add(nature);
            }

            return false;
        }

        public void removeNature(Nature nature) {
            if (natureOnCell.get(nature.getClass().getSimpleName()).remove(nature)) {

            } else {
                System.out.println(nature.getName() + " not removed");
            }
        }
    }
}
