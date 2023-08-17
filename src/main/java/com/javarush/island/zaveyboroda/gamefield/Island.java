package com.javarush.island.zaveyboroda.gamefield;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.plants.PlantFeatures;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.repository.ActionType;
import com.javarush.island.zaveyboroda.repository.DataBase;
import com.javarush.island.zaveyboroda.services.MoveAction;
import com.javarush.island.zaveyboroda.services.EatAction;
import com.javarush.island.zaveyboroda.services.AnimalReproduceAction;
import com.javarush.island.zaveyboroda.services.PlantReproduceAction;
import com.javarush.island.zaveyboroda.view.View;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Island {
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    private final MainController mainController;
    private final DataBase dataBase;
    private final View view;
    private final Cell[][] cells;
    private ScheduledExecutorService executor;

    public Island(MainController mainController) {
        this.mainController = mainController;
        this.dataBase = mainController.getDataBase();
        this.view = mainController.getView();
        this.executor = Executors.newScheduledThreadPool(3);
        cells = new Cell[WIDTH][HEIGHT];
    }

    private void initCellsAndNature() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                cells[i][j] = new Cell(i, j);
                Cell cell = cells[i][j];
                initNatureOnCell(cell, dataBase);
            }
        }
    }

    public void startSimulation() {
        initCellsAndNature();
        List<Nature> allNatureList = updateAllNatureList();

        System.out.println("Day 0: " + allNatureList.size() + " nature on Island");

        for (int i = 0; i < 10; i++) {
            for (Nature nature : allNatureList) {
                ActionType randomActionType = getRandomActionType();

                if (nature instanceof PlantFeatures) randomActionType = ActionType.REPRODUCE;

                Runnable action = createActionInstance(randomActionType, mainController, nature);
                executor.schedule(action, 0, TimeUnit.MILLISECONDS);
                nature.grow(mainController);
            }

            executor.shutdown();

            try {
                executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            allNatureList = updateAllNatureList();
            view.displayState(i, allNatureList);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            executor = Executors.newScheduledThreadPool(3);
        }
    }

    private ActionType getRandomActionType() {
        int randomIndex = ThreadLocalRandom.current().nextInt(ActionType.values().length);
        return ActionType.values()[randomIndex];
    }

    private Runnable createActionInstance(ActionType actionType, MainController controller, Nature nature) {
        switch (actionType) {
            case MOVE -> {
                return new MoveAction(controller, (AnimalFeatures) nature, nature.getCurrentLocation(), cells);
            }
            case EAT -> {
                return new EatAction(controller, (AnimalFeatures) nature, nature.getCurrentLocation());
            }
            case REPRODUCE -> {
                if (nature instanceof AnimalFeatures) {
                    return new AnimalReproduceAction(controller, (AnimalFeatures) nature, nature.getCurrentLocation());
                } else if (nature instanceof PlantFeatures) {
                    return new PlantReproduceAction(controller, (PlantFeatures) nature, nature.getCurrentLocation());
                } else {
                    throw new IllegalArgumentException("Unknown nature type for reproduction: " + nature.getTYPE_NAME());
                }
            }
            default -> throw new IllegalArgumentException("Unknown action type: " + actionType);
        }
    }

    private List<Nature> updateAllNatureList() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .flatMap(cell -> cell.getNatureOnCell().values().stream())
                .flatMap(Collection::stream)
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

    public static class Cell {
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

        public boolean tryAddNature(Nature nature) {
            return natureOnCell.get(nature.getTYPE_NAME()).add(nature);
        }

        public void removeNature(Nature nature) {
            natureOnCell.get(nature.getTYPE_NAME()).remove(nature);
        }
    }
}
