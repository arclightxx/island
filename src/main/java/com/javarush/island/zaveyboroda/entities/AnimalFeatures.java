package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentAge;
import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentWeight;
import com.javarush.island.zaveyboroda.annotations.InjectRandomGender;
import com.javarush.island.zaveyboroda.annotations.NatureFeaturesFieldAnnotationProcessor;
import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DeadCause;
import com.javarush.island.zaveyboroda.repository.Gender;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AnimalFeatures implements Animal, Nature {
    private static int counter = 0;
    private final String name;
    private boolean isAlive = true;
    @InjectRandomCurrentWeight(adultWeightSpread = 0.1, babyWeightSpread = 0.9)
    private double currentWeight;
    private int currentMove;
    @InjectRandomCurrentAge(min = 1)
    private int currentAge;
    private DeadCause deadCause;
    @InjectRandomGender
    private Gender gender = Gender.FEMALE;
    private Island.Cell currentLocation;

    public AnimalFeatures() {
        this.name = this.getClass().getSimpleName() + ++counter;
    }

    public AnimalFeatures(String name, ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        NatureFeaturesFieldAnnotationProcessor.calculateAndSetAnnotatedFields(this, animalFeatures, isBaby);
        this.name = name + ++counter;
        deadCause = DeadCause.ALIVE;
        currentLocation = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalFeatures that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return gender + " "
                + this.getName() + " is alive and "
                + currentWeight + "kg of weight; "
                + currentAge + " years old";
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getName() {
        return name;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(int currentMove) {
        this.currentMove = currentMove;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public DeadCause getDeadCause() {
        return deadCause;
    }

    public void setDeadCause(DeadCause deadCause) {
        this.deadCause = deadCause;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Island.Cell getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Island.Cell currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public void grow(MainController controller) {
        currentAge++;
        if (currentAge == controller.getDataBase()
                .getConstantNaturesFeaturesMap()
                .get(this.getClass().getSimpleName())
                .getMAX_AGE()) {
            deadCause = DeadCause.DIED_NATURALLY;
            isAlive = false;
            currentLocation.removeNature(this);

            System.out.println(name + " " + deadCause + " on [" + currentLocation.getX() + "," + currentLocation.getY() + "] at age " + currentAge);
        }
    }

    @Override
    public void move(MainController controller, Island.Cell[][] cells) {
        if (!calculateRandomMove(controller)) {
//            System.out.println(name + " stay on it's field [" + currentLocation.getX() + "," + currentLocation.getY() + "]");
            return;
        }

        int oldX = currentLocation.getX();
        int oldY = currentLocation.getY();


        int[] newLocation = calculateNewLocation();
        int newX = newLocation[0];
        int newY = newLocation[1];

        if (cells[newX][newY].tryAddNature(this)) {
            cells[oldX][oldY].removeNature(this);
            currentLocation = cells[newX][newY];
//            System.out.println(name + " moved from [" + oldX + "," + oldY + "] to [" + newX + "," + newY + "]");
        } else {
//            System.out.println(name + " can't move to [" + newX + "," + newY + "] location - it's full");
        }

        this.grow(controller);
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
                .get(this.getClass().getSimpleName())
                .getMAX_TRAVEL_SPEED();

        currentMove = maxMove > 0 ? ThreadLocalRandom.current().nextInt(0, maxMove) : 0;

        return currentMove > 0;
    }

    public void eat() {

    }
}
