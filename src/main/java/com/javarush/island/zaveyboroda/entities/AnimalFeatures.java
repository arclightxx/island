package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DataBase;
import com.javarush.island.zaveyboroda.repository.DeadCause;
import com.javarush.island.zaveyboroda.repository.Gender;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class AnimalFeatures extends NatureAbstractClass implements Animal {
    public static int eatCounter = 0;
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
