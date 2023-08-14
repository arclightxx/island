package com.javarush.island.zaveyboroda.entities.plants;

import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.NatureAbstractClass;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public abstract class PlantFeatures extends NatureAbstractClass implements Nature {
    public PlantFeatures(String name, ConstantNatureFeatures constantNatureFeatures, Island.Cell cell, boolean isBaby) {
        super(name, constantNatureFeatures, cell, isBaby);
    }
}
