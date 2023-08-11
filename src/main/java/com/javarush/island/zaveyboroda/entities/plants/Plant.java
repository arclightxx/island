package com.javarush.island.zaveyboroda.entities.plants;

import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Plant extends PlantFeatures implements Nature {
    public Plant(String name, ConstantNatureFeatures constantNatureFeatures, Island.Cell cell, boolean isBaby) {
        super(name, constantNatureFeatures, cell, isBaby);
    }

    @Override
    public void reproduction(Nature father, Nature mother) {

    }
}
