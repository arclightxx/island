package com.javarush.island.zaveyboroda.factory;

import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.NatureFeatures;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DataBase;

import java.lang.reflect.Constructor;

public class NatureFactory {

    public static <T extends NatureFeatures> T createNature(Class<? extends Nature> natureClass, DataBase db) {
        try {
            Constructor<?> natureConstructor = natureClass.getDeclaredConstructor(ConstantNatureFeatures.class);
            String natureClassName = natureClass.getSimpleName();
            ConstantNatureFeatures natureFeature = db.getConstantNaturesFeaturesMap().get(natureClassName);

            return (T) natureConstructor.newInstance(natureFeature);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create animal instance " + natureClass.getSimpleName() + e.getMessage());
        }
    }
}
