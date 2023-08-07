package com.javarush.island.zaveyboroda.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Sheep extends NatureFeatures implements Herbivore {
    public Sheep(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }
}
