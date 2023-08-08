package com.javarush.island.zaveyboroda.entities.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Caterpillar extends NatureFeatures implements Herbivore {
    public Caterpillar(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }
}
