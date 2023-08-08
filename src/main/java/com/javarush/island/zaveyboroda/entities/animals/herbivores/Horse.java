package com.javarush.island.zaveyboroda.entities.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Horse extends NatureFeatures implements Herbivore {
    public Horse(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }
}
