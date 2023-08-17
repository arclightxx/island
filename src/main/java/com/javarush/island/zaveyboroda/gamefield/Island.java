package com.javarush.island.zaveyboroda.gamefield;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.plants.PlantFeatures;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.repository.enums.ActionType;
import com.javarush.island.zaveyboroda.repository.database.DataBase;
import com.javarush.island.zaveyboroda.services.actions.MoveAction;
import com.javarush.island.zaveyboroda.services.actions.EatAction;
import com.javarush.island.zaveyboroda.services.actions.AnimalReproduceAction;
import com.javarush.island.zaveyboroda.services.actions.PlantReproduceAction;
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

    public Island(MainController mainController) {
        this.mainController = mainController;
        this.dataBase = mainController.getDataBase();
        this.view = mainController.getView();
        cells = new Cell[WIDTH][HEIGHT];
    }

    private void initCellsAndNature() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                cells[i][j] = new Cell(i, j);
                Cell cell = cells[i][j];
                cell.initNatureOnCell(dataBase);
            }
        }
    }

    public void startSimulation() {
        initCellsAndNature();

        List<Nature> allNatureList = updateAllNatureList();
        view.dayZeroDisplayState(allNatureList.size());

        for (int i = 0; i < 10; i++) {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
            executeActionsForEachNature(allNatureList, executor);

            executor.shutdown();
            boolean isExecTerminated = false;
            try {
                isExecTerminated = executor.awaitTermination(10000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isExecTerminated) {
                allNatureList = updateAllNatureList();
                view.displayState(i, allNatureList);
            } else {
                List<Runnable> notExecuted = executor.shutdownNow();
                System.err.println("Executor not terminated; Not executed tasks: " + notExecuted.size());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executeActionsForEachNature(List<Nature> allNatureList, ScheduledExecutorService executor) {
        for (Nature nature : allNatureList) {
            ActionType randomActionType = getRandomActionType();

            if (nature instanceof PlantFeatures) randomActionType = ActionType.REPRODUCE;

            Runnable action = createActionInstance(randomActionType, mainController, nature);
            executor.schedule(action, 0, TimeUnit.MILLISECONDS);
            nature.grow(mainController);
        }
    }

    private ActionType getRandomActionType() {
        int randomIndex = ThreadLocalRandom.current().nextInt(ActionType.values().length);
        return ActionType.values()[randomIndex];
    }

    private Runnable createActionInstance(ActionType actionType, MainController controller, Nature nature) {
        Cell currLocation = nature.getCurrentLocation();

        switch (actionType) {
            case MOVE -> {
                return new MoveAction(controller, (AnimalFeatures) nature, currLocation, cells);
            }
            case EAT -> {
                return new EatAction(controller, (AnimalFeatures) nature, currLocation);
            }
            case REPRODUCE -> {
                if (nature instanceof AnimalFeatures) {
                    return new AnimalReproduceAction(controller, (AnimalFeatures) nature, currLocation);
                } else if (nature instanceof PlantFeatures) {
                    return new PlantReproduceAction(controller, (PlantFeatures) nature, currLocation);
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

        private void initNatureOnCell(DataBase db) {
            HashMap<String, HashSet<Nature>> naturesOnCell = new HashMap<>();
            HashMap<String, Class<Nature>> natureClassesMap = db.getNatureClassesMap();

            for (String key: natureClassesMap.keySet()) {
                Class<? extends Nature> natureClass = db.getNatureClassesMap().get(key);

                if (db.getNaturesConfigMap().get(key) == 0) {
                    naturesOnCell.put(key, new HashSet<>());
                }

                HashSet<Nature> naturesSet = new HashSet<>();
                for (int i = 0; i < db.getNaturesConfigMap().get(key); i++){
                    Nature natureObject = NatureFactory.createNature(natureClass, db, this, false);

                    naturesSet.add(natureObject);
                    naturesOnCell.put(key, naturesSet);
                }
            }

            setNatureOnCell(naturesOnCell);
        }

        public boolean tryAddNature(Nature nature) {
            return natureOnCell.get(nature.getTYPE_NAME()).add(nature);
        }

        public void removeNature(Nature nature) {
            natureOnCell.get(nature.getTYPE_NAME()).remove(nature);
        }
    }
}
