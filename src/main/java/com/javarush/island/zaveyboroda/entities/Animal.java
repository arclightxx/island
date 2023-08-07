package com.javarush.island.zaveyboroda.entities;

public interface Animal extends Nature {
    default void moveDirection() {

    }

    default void eat() {};

}
