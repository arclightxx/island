package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentAge;
import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentWeight;
import com.javarush.island.zaveyboroda.annotations.NatureFeaturesFieldAnnotationProcessor;
import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DeadCause;

import java.util.*;

public abstract class NatureAbstractClass implements Nature {
    private static int counter = 0;
    private final String UNIQUE_NAME;
    private final String TYPE_NAME;
    private boolean isAlive = true;
    @InjectRandomCurrentWeight(adultWeightSpread = 0.1, babyWeightSpread = 0.9)
    private double currentWeight;
    @InjectRandomCurrentAge(min = 1)
    private int currentAge;
    private final int MAX_AGE;
    private DeadCause deadCause;
    private Island.Cell currentLocation;

    public NatureAbstractClass(String name, ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        NatureFeaturesFieldAnnotationProcessor.calculateAndSetAnnotatedFields(this, animalFeatures, isBaby);
        UNIQUE_NAME = name + ++counter;
        TYPE_NAME = this.getClass().getSimpleName();
        MAX_AGE = animalFeatures.getMAX_AGE();
        deadCause = DeadCause.ALIVE;
        currentLocation = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalFeatures that)) return false;
        return Objects.equals(UNIQUE_NAME, that.getTYPE_NAME());
    }

    @Override
    public int hashCode() {
        return Objects.hash(UNIQUE_NAME);
    }

    @Override
    public void grow(MainController controller) {
        currentAge++;
        if (currentAge == MAX_AGE) {
            die(DeadCause.DIED_NATURALLY);

            System.out.println(UNIQUE_NAME + " " + deadCause + " on [" + currentLocation.getX() + "," + currentLocation.getY() + "] at age " + currentAge);
        }
    }

    @Override
    public void die(DeadCause deadCause) {
        this.deadCause = deadCause;
        isAlive = false;
        currentLocation.removeNature(this);
    }

    @Override
    public String getUNIQUE_NAME() {
        return UNIQUE_NAME;
    }

    @Override
    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public int getMAX_AGE() {
        return MAX_AGE;
    }

    public DeadCause getDeadCause() {
        return deadCause;
    }

    public void setDeadCause(DeadCause deadCause) {
        this.deadCause = deadCause;
    }

    @Override
    public Island.Cell getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Island.Cell currentLocation) {
        this.currentLocation = currentLocation;
    }
}
