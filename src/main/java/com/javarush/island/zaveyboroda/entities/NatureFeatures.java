package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentAge;
import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentWeight;
import com.javarush.island.zaveyboroda.annotations.InjectRandomGender;
import com.javarush.island.zaveyboroda.annotations.NatureFeaturesFieldAnnotationProcessor;
import com.javarush.island.zaveyboroda.gamefield.Cell;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DeadCause;
import com.javarush.island.zaveyboroda.repository.Gender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public abstract class NatureFeatures implements Animal {
    private boolean isAlive = true;
    @InjectRandomCurrentWeight(min = 0.0, max = 0.1)
    private double currentWeight = 0.0;
    private int currentMove = 0;
    @InjectRandomCurrentAge(min = 1)
    private int currentAge = 1;
    private DeadCause deadCause;
    private HashMap<String, Integer> preferableFood = new HashMap<>();
    @InjectRandomGender
    private Gender gender = Gender.FEMALE;
    private Cell currentLocation;
    private Cell[][] neighbours;

    public NatureFeatures() {

    }

    public NatureFeatures(ConstantNatureFeatures animalFeatures) {
        NatureFeaturesFieldAnnotationProcessor.calculateAndSetAnnotatedFields(this, animalFeatures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NatureFeatures that)) return false;
        return isAlive == that.isAlive && Double.compare(currentWeight, that.currentWeight) == 0 && currentMove == that.currentMove && currentAge == that.currentAge && deadCause == that.deadCause && Objects.equals(preferableFood, that.preferableFood) && gender == that.gender && Objects.equals(currentLocation, that.currentLocation) && Arrays.deepEquals(neighbours, that.neighbours);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(isAlive, currentWeight, currentMove, currentAge, deadCause, preferableFood, gender, currentLocation);
        result = 31 * result + Arrays.deepHashCode(neighbours);
        return result;
    }

    public String toString() {
        return gender + " "
                + this.getClass().getSimpleName() + " is alive and "
                + currentWeight + "kg of weight; "
                + currentAge + " years old";
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

    public HashMap<String, Integer> getPreferableFood() {
        return preferableFood;
    }

    public void setPreferableFood(HashMap<String, Integer> preferableFood) {
        this.preferableFood = preferableFood;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Cell getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Cell currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Cell[][] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Cell[][] neighbours) {
        this.neighbours = neighbours;
    }

//    private double getRandomCurrentWeight(ConstantNatureFeatures animalFeatures) {
//        double randomWeight = animalFeatures.getMAX_WEIGHT() - (animalFeatures.getMAX_WEIGHT() * Math.random() * 0.1);
//
//        return Math.round(randomWeight*1000.0)/1000.0;
//    }
}
