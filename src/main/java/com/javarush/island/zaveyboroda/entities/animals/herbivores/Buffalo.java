package com.javarush.island.zaveyboroda.entities.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Buffalo extends AnimalFeatures implements Herbivore {
    public Buffalo(String name, ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        super(name, animalFeatures, cell, isBaby);
    }
}
