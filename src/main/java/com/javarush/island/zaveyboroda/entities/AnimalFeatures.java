package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentAge;
import com.javarush.island.zaveyboroda.annotations.InjectRandomCurrentWeight;
import com.javarush.island.zaveyboroda.annotations.InjectRandomGender;
import com.javarush.island.zaveyboroda.annotations.NatureFeaturesFieldAnnotationProcessor;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DeadCause;
import com.javarush.island.zaveyboroda.repository.Gender;

import java.util.Objects;

public abstract class AnimalFeatures implements Nature {
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

    }

    public AnimalFeatures(ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        NatureFeaturesFieldAnnotationProcessor.calculateAndSetAnnotatedFields(this, animalFeatures, isBaby);
        deadCause = DeadCause.ALIVE;
        currentLocation = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalFeatures that)) return false;
        return isAlive == that.isAlive && Double.compare(currentWeight, that.currentWeight) == 0 && currentMove == that.currentMove && currentAge == that.currentAge && deadCause == that.deadCause && gender == that.gender && Objects.equals(currentLocation, that.currentLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlive, currentWeight, currentMove, currentAge, deadCause, gender, currentLocation);
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
}
