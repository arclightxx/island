package com.javarush.island.zaveyboroda.entities.plants;

import com.javarush.island.zaveyboroda.annotations.CalculateRandomAgeProcessor;
import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentAge;
import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DeadCause;

import java.util.Objects;

public abstract class PlantFeatures implements Nature {
    private static int counter = 0;
    private final String uniqueName;
    private final String typeName;
    private boolean isAlive = true;
    private double currentWeight;
    @InjectRandomCurrentAge(min = 10)
    private int currentAge;
    private DeadCause deadCause;
    private Island.Cell currentLocation;

    public PlantFeatures(String name, ConstantNatureFeatures constantNatureFeatures, Island.Cell cell, boolean isBaby) {
        CalculateRandomAgeProcessor.calculateAndSetRandomCurrentAge(this, constantNatureFeatures, isBaby);
        uniqueName = name + ++counter;
        typeName = this.getClass().getSimpleName();
        currentWeight = constantNatureFeatures.getMAX_WEIGHT();
        deadCause = DeadCause.ALIVE;
        currentLocation = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlantFeatures that)) return false;
        return Objects.equals(uniqueName, that.uniqueName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueName);
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
    public String getUniqueName() {
        return uniqueName;
    }

    public String getTypeName() {
        return typeName;
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

    public DeadCause getDeadCause() {
        return deadCause;
    }

    public void setDeadCause(DeadCause deadCause) {
        this.deadCause = deadCause;
    }

    public Island.Cell getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Island.Cell currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public void grow(MainController controller) {
        currentAge ++;
        if (currentAge == controller.getDataBase()
                .getConstantNaturesFeaturesMap()
                .get(typeName)
                .getMAX_AGE()) {
            deadCause = DeadCause.DIED_NATURALLY;
            isAlive = false;
            currentLocation.removeNature(this);

            System.out.println(uniqueName + " " + deadCause + " on " + currentLocation.getX() + "," + currentLocation.getY() + " at age " + currentAge);
        }
    }
}
