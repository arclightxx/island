package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.gamefield.Island;

public interface Animal extends Nature {
    default void moveDirection() {

    }

    default void eat() {}
}
