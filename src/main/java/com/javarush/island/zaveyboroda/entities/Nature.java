package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.gamefield.Island;

public interface Nature {
    default void reproduction(Nature father, Nature mother) {

    }
}
