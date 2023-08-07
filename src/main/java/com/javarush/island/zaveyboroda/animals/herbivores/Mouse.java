package com.javarush.island.zaveyboroda.animals.herbivores;

import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

public class Mouse extends NatureFeatures implements Herbivore {
    public Mouse(ConstantNatureFeatures animalFeatures) {
        super(animalFeatures);
    }
}
