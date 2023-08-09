package com.javarush.island.zaveyboroda.entities;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;

public interface Animal extends Nature {
    void move(MainController controller, Island.Cell[][] cells);
    default void moveDirection() {

    }

    default void eat() {}
}
