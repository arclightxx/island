package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DataBase;
import com.javarush.island.zaveyboroda.repository.DeadCause;
import com.javarush.island.zaveyboroda.repository.Gender;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AnimalFeatures extends NatureAbstractClass implements Animal {
    public static int eatCounter = 0;
    private final double AMOUNT_OF_FOOD_TO_FILL;
    private int currentMove;
    private final Gender GENDER;

    public AnimalFeatures(String name, ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        super(name, animalFeatures, cell, isBaby);

        AMOUNT_OF_FOOD_TO_FILL = animalFeatures.getAMOUNT_OF_FOOD_TO_FILL();
        GENDER = Math.random() > 0.5 ? Gender.MALE : Gender.FEMALE;
    }

    public String toString() {
        return GENDER
                + getTYPE_NAME() + " is " + getDeadCause()
                + getCurrentWeight() + "kg of weight; "
                + getCurrentAge() + " years old";
    }

    public Gender getGENDER() {
        return GENDER;
    }

    @Override
    public void move(MainController controller, Island.Cell[][] cells) {
        if (!calculateRandomMove(controller)) {
//            System.out.println(name + " stay on it's field [" + currentLocation.getX() + "," + currentLocation.getY() + "]");
            return;
        }

        int oldX = getCurrentLocation().getX();
        int oldY = getCurrentLocation().getY();


        int[] newLocation = calculateNewLocation();
        int newX = newLocation[0];
        int newY = newLocation[1];

        if (cells[newX][newY].tryAddNature(this)) {
            cells[oldX][oldY].removeNature(this);
            setCurrentLocation(cells[newX][newY]);
//            System.out.println(name + " moved from [" + oldX + "," + oldY + "] to [" + newX + "," + newY + "]");
        } else {
//            System.out.println(name + " can't move to [" + newX + "," + newY + "] location - it's full");
        }

        this.grow(controller);
    }

    private int[] calculateNewLocation() {
        int[] newLocation = new int[2];
        newLocation[0] = getCurrentLocation().getX();
        newLocation[1] = getCurrentLocation().getY();

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
                .get(getTYPE_NAME())
                .getMAX_TRAVEL_SPEED();

        currentMove = maxMove > 0 ? ThreadLocalRandom.current().nextInt(0, maxMove) : 0;

        return currentMove > 0;
    }

    public void eat(MainController controller) {
        DataBase dataBase = controller.getDataBase();
        HashMap<String, HashMap<String, Integer>> preferableFood = dataBase.getPreferableFoodMap();
        HashMap<String, Integer> currAnimalPrefFood = preferableFood.get(getTYPE_NAME());
        HashMap<String, HashSet<Nature>> natureOnCell = getCurrentLocation().getNatureOnCell();

        Optional<Nature> optionalNatureToEat = natureOnCell.values().stream()
                .flatMap(Collection::stream)
                .filter(nature -> canEat(nature, currAnimalPrefFood))
                .findFirst();

        if (optionalNatureToEat.isEmpty()) {
            System.out.println(this.getUNIQUE_NAME() + " couldn't eat");
        } else {
            Nature natureToEat = optionalNatureToEat.get();
            natureToEat.die(DeadCause.HAS_BEEN_EATEN);
            setCurrentWeight(AMOUNT_OF_FOOD_TO_FILL);
            eatCounter++;

            System.out.println(natureToEat.getUNIQUE_NAME() + " " + DeadCause.HAS_BEEN_EATEN + " by " + getUNIQUE_NAME()
            + " on [" + getCurrentLocation().getX() + "," + getCurrentLocation().getY() + "]");
        }
    }

    private boolean canEat(Nature nature, HashMap<String, Integer> currAnimalPrefFood) {
        int eatRandomChance = ThreadLocalRandom.current().nextInt(0, 100);
        String natureName = nature.getTYPE_NAME();
        int eatChanceOfNature = currAnimalPrefFood.get(natureName);

        return eatRandomChance <= eatChanceOfNature && eatChanceOfNature != 0;
    }
}
