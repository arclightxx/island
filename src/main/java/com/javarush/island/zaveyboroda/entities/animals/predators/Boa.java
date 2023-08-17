package com.javarush.island.zaveyboroda.entities.animals.predators;

import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.Predator;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.database.ConstantNatureFeatures;

public class Boa extends AnimalFeatures implements Predator {

    public Boa(String name, ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        super(name, animalFeatures, cell, isBaby);
    }
}
