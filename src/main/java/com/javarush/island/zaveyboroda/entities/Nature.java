package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.enums.DeadCause;

public interface Nature {
    void grow(MainController controller);
    void die(DeadCause deadCause);
    boolean isAlive();
    double getCurrentWeight();
    String getUNIQUE_NAME();
    String getTYPE_NAME();
    Island.Cell getCurrentLocation();
}
