package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.DeadCause;

public interface Nature {
    default void reproduction(Nature father, Nature mother) {

    }
    void grow(MainController controller);
    void die(DeadCause deadCause);

    String getUNIQUE_NAME();
    String getTYPE_NAME();
    Island.Cell getCurrentLocation();
}
