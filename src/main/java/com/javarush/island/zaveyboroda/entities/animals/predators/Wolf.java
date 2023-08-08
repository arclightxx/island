package com.javarush.island.zaveyboroda.entities.animals.predators;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.Predator;

public class Wolf extends NatureFeatures implements Predator {

    public Wolf(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }

    @Override
    public void eat() {

    }

    @Override
    public void reproduction(Nature father, Nature mother) {

    }
}
