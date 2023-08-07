package com.javarush.island.zaveyboroda.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConstantNatureFeatures {
    private double MAX_WEIGHT;
    private int MAX_AMOUNT_ON_CELL;
    private int MAX_TRAVEL_SPEED;
    private double AMOUNT_OF_FOOD_TO_FILL;
    private int MAX_AGE;

    public ConstantNatureFeatures() {
    }

    public double getMAX_WEIGHT() {
        return MAX_WEIGHT;
    }

    public int getMAX_AMOUNT_ON_CELL() {
        return MAX_AMOUNT_ON_CELL;
    }

    public int getMAX_TRAVEL_SPEED() {
        return MAX_TRAVEL_SPEED;
    }

    public double getAMOUNT_OF_FOOD_TO_FILL() {
        return AMOUNT_OF_FOOD_TO_FILL;
    }

    public int getMAX_AGE() {
        return MAX_AGE;
    }

    @JsonProperty("MAX_WEIGHT")
    public void setMAX_WEIGHT(double MAX_WEIGHT) {
        this.MAX_WEIGHT = MAX_WEIGHT;
    }

    @JsonProperty("MAX_AMOUNT_ON_CELL")
    public void setMAX_AMOUNT_ON_CELL(int MAX_AMOUNT_ON_CELL) {
        this.MAX_AMOUNT_ON_CELL = MAX_AMOUNT_ON_CELL;
    }

    @JsonProperty("MAX_TRAVEL_SPEED")
    public void setMAX_TRAVEL_SPEED(int MAX_TRAVEL_SPEED) {
        this.MAX_TRAVEL_SPEED = MAX_TRAVEL_SPEED;
    }

    @JsonProperty("AMOUNT_OF_FOOD_TO_FILL")
    public void setAMOUNT_OF_FOOD_TO_FILL(double AMOUNT_OF_FOOD_TO_FILL) {
        this.AMOUNT_OF_FOOD_TO_FILL = AMOUNT_OF_FOOD_TO_FILL;
    }

    @JsonProperty("MAX_AGE")
    public void setMAX_AGE(int MAX_AGE) {
        this.MAX_AGE = MAX_AGE;
    }

    @Override
    public String toString() {
        return "Max Weight: " + getMAX_WEIGHT()
                + "\nMax Amount On Cell: " + getMAX_AMOUNT_ON_CELL()
                + "\nMax Travel Speed: " + getMAX_TRAVEL_SPEED()
                + "\nAmount Of Food To Fill: " + getAMOUNT_OF_FOOD_TO_FILL()
                + "\nMax Age: " + getMAX_AGE();
    }
}
