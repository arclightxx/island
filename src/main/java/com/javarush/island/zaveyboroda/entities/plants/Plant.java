package com.javarush.island.zaveyboroda.entities.plants;

import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Plant extends PlantFeatures implements Nature {
    public Plant(ConstantNatureFeatures natureFeature) {
        super(natureFeature);
    }

    @Override
    public void reproduction(Nature father, Nature mother) {

    }

    @Override
    public boolean isEaten() {
        return false;
    }

    @Override
    public boolean isDiedOfHunger() {
        return false;
    }

    @Override
    public boolean isDiedNaturally() {
        return false;
    }
}
