package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.database.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.enums.Gender;

public abstract class AnimalFeatures extends NatureAbstractClass implements Animal {
    private final double AMOUNT_OF_FOOD_TO_FILL;
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

    public double getAMOUNT_OF_FOOD_TO_FILL() {
        return AMOUNT_OF_FOOD_TO_FILL;
    }

    public Gender getGENDER() {
        return GENDER;
    }
}
