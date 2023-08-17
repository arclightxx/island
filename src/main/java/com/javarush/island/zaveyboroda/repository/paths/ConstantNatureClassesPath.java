package com.javarush.island.zaveyboroda.repository.paths;

public class ConstantNatureClassesPath {
    private static final String[] natureClassesPath = {"com.javarush.island.zaveyboroda.entities.animals.predators.",
            "com.javarush.island.zaveyboroda.entities.animals.herbivores.",
            "com.javarush.island.zaveyboroda.entities.animals.omnivores.",
            "com.javarush.island.zaveyboroda.entities.plants." };

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
