package com.javarush.island.zaveyboroda.repository;

public class ConstantNatureClassesPath {
    private static final String[] natureClassesPath = {"com.javarush.island.zaveyboroda.animals.predators.",
            "com.javarush.island.zaveyboroda.animals.herbivores.",
            "com.javarush.island.zaveyboroda.animals.omnivores.",
            "com.javarush.island.zaveyboroda.plants." };

    private ConstantNatureClassesPath() {}

    public static Class<?> classFinder(String className) throws ClassNotFoundException {
        for (String path : natureClassesPath) {
            try {
                return Class.forName(path + className);
            } catch (ClassNotFoundException ignored) {
            }
        }
        throw new ClassNotFoundException();
    }
}
