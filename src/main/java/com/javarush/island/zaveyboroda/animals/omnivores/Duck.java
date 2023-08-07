package com.javarush.island.zaveyboroda.animals.omnivores;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.entities.Predator;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Duck extends NatureFeatures implements Predator, Herbivore {
    public Duck(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }
}
