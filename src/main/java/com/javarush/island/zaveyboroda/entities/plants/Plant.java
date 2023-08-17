package com.javarush.island.zaveyboroda.entities.plants;

import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.database.ConstantNatureFeatures;

public class Plant extends PlantFeatures {
    public Plant(String name, ConstantNatureFeatures constantNatureFeatures, Island.Cell cell, boolean isBaby) {
        super(name, constantNatureFeatures, cell, isBaby);
    }
}
