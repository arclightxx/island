package com.javarush.island.zaveyboroda.entities.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.database.ConstantNatureFeatures;

public class Caterpillar extends AnimalFeatures implements Herbivore {
    public Caterpillar(String name, ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        super(name, animalFeatures, cell, isBaby);
    }
}
