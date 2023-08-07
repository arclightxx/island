package com.javarush.island.zaveyboroda.entities;

public interface Nature {
    default void reproduction(Nature father, Nature mother) {

    };

    default boolean isEaten() {
        return true;
    }

    default boolean isDiedOfHunger(){return true;}

    default boolean isDiedNaturally() {
        return true;
    };
}
