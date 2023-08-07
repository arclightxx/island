package com.javarush.island.zaveyboroda.plants;

import com.javarush.island.zaveyboroda.annotations.CalculateRandomAgeProcessor;
import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentAge;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.gamefield.Cell;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

import java.util.Objects;
import java.util.Random;

public abstract class PlantFeatures implements Nature {
    private boolean isAlive = true;
    private double currentWeight;
    @InjectRandomCurrentAge(min = 10)
    private int currentAge;
    private Cell currentLocation;

    public PlantFeatures(ConstantNatureFeatures constantNatureFeatures) {
        currentWeight = constantNatureFeatures.getMAX_WEIGHT();
        CalculateRandomAgeProcessor.calculateAndSetRandomCurrentAge(this, constantNatureFeatures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantFeatures that)) return false;
        return isAlive == that.isAlive && Double.compare(currentWeight, that.currentWeight) == 0 && currentAge == that.currentAge && Objects.equals(currentLocation, that.currentLocation);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(isAlive, currentWeight, currentAge, currentLocation);
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "PlantFeatures{" +
                "currentWeight=" + currentWeight +
                ", currentAge=" + currentAge +
                '}';
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

    public Cell getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Cell currentLocation) {
        this.currentLocation = currentLocation;
    }
}
