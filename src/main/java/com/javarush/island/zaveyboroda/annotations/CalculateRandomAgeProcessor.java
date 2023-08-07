package com.javarush.island.zaveyboroda.annotations;

import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;

import java.lang.reflect.Field;
import java.util.Random;

public class CalculateRandomAgeProcessor {
    private CalculateRandomAgeProcessor() {

    }

    public static void calculateAndSetRandomCurrentAge(Object object, ConstantNatureFeatures constantNatureFeatures) {
        Class<?> clazz = object.getClass().getSuperclass();
        Field[] fields = clazz.getDeclaredFields();
        Random random = new Random();

        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectRandomCurrentAge.class)) {
                InjectRandomCurrentAge ageAnnotation = field.getAnnotation(InjectRandomCurrentAge.class);
                int MAX_AGE = constantNatureFeatures.getMAX_AGE();
                int min = ageAnnotation.min();

                int result = random.nextInt(MAX_AGE - min) + min;

                try {
                    field.setAccessible(true);
                    field.set(object, result);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Couldn't set currentAge value" + "\n" + e.getMessage());
                }
            }
        }

    }
}
