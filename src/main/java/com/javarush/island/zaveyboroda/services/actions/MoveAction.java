package com.javarush.island.zaveyboroda.services.actions;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.gamefield.Island;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoveAction implements Runnable {
    private final MainController controller;
    private final AnimalFeatures animal;
    private final Island.Cell currentLocation;
    private final Island.Cell[][] cells;
    private int currentMove;

    public MoveAction(MainController controller, AnimalFeatures animal, Island.Cell currentLocation, Island.Cell[][] cells) {
        this.controller = controller;
        this.animal = animal;
        this.currentLocation = currentLocation;
        this.cells = cells;
    }

    @Override
    public void run() {
        synchronized (currentLocation) {
            if (!calculateRandomMove(controller) || !animal.isAlive()) {
//            System.out.println(name + " stay on it's field [" + currentLocation.getX() + "," + currentLocation.getY() + "]");
                return;
            }

            int oldX = currentLocation.getX();
            int oldY = currentLocation.getY();
            
            int[] newLocation = calculateNewLocation();
            int newX = newLocation[0];
            int newY = newLocation[1];

            if (cells[newX][newY].getNatureOnCell().get(animal.getTYPE_NAME()).size() < animal.getMAX_AMOUNT_ON_CELL()) {
                if (cells[newX][newY].tryAddNature(animal)) {
                    cells[oldX][oldY].removeNature(animal);
                    animal.setCurrentLocation(cells[newX][newY]);
//                    System.out.println(animal.getUNIQUE_NAME() + " move from [" + oldX + "," + oldY + "] to [" + newX + "," + newY + "]");
                }
            } else {
//            System.out.println(getUNIQUE_NAME() + " can't move to [" + newX + "," + newY + "] location - it's full");
            }
        }
    }

    private int[] calculateNewLocation() {
        int[] newLocation = new int[2];
        newLocation[0] = currentLocation.getX();
        newLocation[1] = currentLocation.getY();

        int newX = newLocation[0];
        int newY = newLocation[1];

        List<Integer> directionsPool = Arrays.asList(0, 1, 2 , 3);

        for (int i = 0; i < currentMove; i++) {
            Collections.shuffle(directionsPool);
            
            for (int direction : directionsPool) {
                if (direction == 0 && canMoveUp(newX)) {
                    newX = moveUp(newX);
                    break;
                } else if (direction == 1 && canMoveRight(newY)) {
                    newY = moveRight(newY);
                    break;
                } else if (direction == 2 && canMoveDown(newX)) {
                    newX = moveDown(newX);
                    break;
                } else if (direction == 3 && canMoveLeft(newY)) {
                    newY = moveLeft(newY);
                    break;
                }
            }
        }

        newLocation[0] = newX;
        newLocation[1] = newY;

        return newLocation;
    }

    private boolean canMoveLeft(int newY) {
        return newY-1 > 0;
    }

    private boolean canMoveDown(int newX) {
        return newX+1 < Island.HEIGHT;
    }

    private boolean canMoveRight(int newY) {
        return newY+1 < Island.WIDTH;
    }

    private boolean canMoveUp(int newX) {
        return newX-1 > 0;
    }

    private int moveUp(int newX) {
        return newX-1;
    }

    public int moveRight(int newY) {
        return newY+1;
    }

    public int moveDown(int newX) {
        return newX+1;
    }

    public int moveLeft(int newY) {
        return newY-1;
    }

    private boolean calculateRandomMove(MainController controller) {
        int maxMove = controller.getDataBase()
                .getConstantNaturesFeaturesMap()
                .get(animal.getTYPE_NAME())
                .getMAX_TRAVEL_SPEED();

        currentMove = maxMove > 0 ? ThreadLocalRandom.current().nextInt(0, maxMove) : 0;

        return currentMove > 0;
    }
}
