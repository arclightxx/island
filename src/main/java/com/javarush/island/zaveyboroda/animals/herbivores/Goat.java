package com.javarush.island.zaveyboroda.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Goat extends NatureFeatures implements Herbivore {
    public Goat(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }
}
