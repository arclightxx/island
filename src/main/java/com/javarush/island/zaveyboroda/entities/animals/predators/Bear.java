package com.javarush.island.zaveyboroda.entities.animals.predators;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.Predator;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Bear extends NatureFeatures implements Predator {
    public Bear(ConstantNatureFeatures animalFeature) {
        super(animalFeature);
    }

    @Override
    public void eat() {

    }

    @Override
    public void reproduction(Nature father, Nature mother) {

    }
}
