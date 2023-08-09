package com.javarush.island.zaveyboroda.entities.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Caterpillar extends AnimalFeatures implements Herbivore {
    public Caterpillar(ConstantNatureFeatures animalFeatures, Island.Cell cell, boolean isBaby) {
        super(animalFeatures, cell, isBaby);
    }
}
