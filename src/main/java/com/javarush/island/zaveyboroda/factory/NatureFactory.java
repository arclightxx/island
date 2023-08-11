package com.javarush.island.zaveyboroda.factory;

import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DataBase;

import java.lang.reflect.Constructor;

public class NatureFactory {

    public static <T extends Nature> T createNature(Class<? extends Nature> natureClass, DataBase db, Island.Cell cell, boolean isBaby) {
        try {
            Constructor<?> natureConstructor = natureClass.getDeclaredConstructor(String.class, ConstantNatureFeatures.class, Island.Cell.class, boolean.class);
            String natureClassName = natureClass.getSimpleName();
            ConstantNatureFeatures natureFeature = db.getConstantNaturesFeaturesMap().get(natureClassName);

            return (T) natureConstructor.newInstance(natureClassName, natureFeature, cell, isBaby);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create animal instance " + natureClass.getSimpleName() + e.getMessage());
        }
    }
}
