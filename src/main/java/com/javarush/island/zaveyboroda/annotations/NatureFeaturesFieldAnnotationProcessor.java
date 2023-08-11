package com.javarush.island.zaveyboroda.annotations;

import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.Gender;

import java.lang.reflect.Field;

public class NatureFeaturesFieldAnnotationProcessor {
    private NatureFeaturesFieldAnnotationProcessor() {

    }

    public static void calculateAndSetAnnotatedFields(Object object, ConstantNatureFeatures constantNatureFeatures, boolean isBaby) {
        Class<?> clazz = object.getClass().getSuperclass().getSuperclass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectRandomCurrentWeight.class)) {
                calculateAndSetRandomCurrentWeight(object, constantNatureFeatures, field, isBaby);
            } else if (field.isAnnotationPresent(InjectRandomCurrentAge.class)) {
                CalculateRandomAgeProcessor.calculateAndSetRandomCurrentAge(object, constantNatureFeatures, isBaby);
            }
        }
    }

    private static void calculateAndSetRandomCurrentWeight(Object object, ConstantNatureFeatures constantNatureFeatures, Field field, boolean isBaby) {
        InjectRandomCurrentWeight weightAnnotation = field.getAnnotation(InjectRandomCurrentWeight.class);
        double MAX_WEIGHT = constantNatureFeatures.getMAX_WEIGHT();
        double adultWeightSpread = weightAnnotation.adultWeightSpread();
        double babyWeightSpread = weightAnnotation.babyWeightSpread();

        double result = isBaby ? MAX_WEIGHT - MAX_WEIGHT * (Math.random() * babyWeightSpread) : MAX_WEIGHT - MAX_WEIGHT * (Math.random() * adultWeightSpread);
        result = Math.round(result * 1000.0) / 1000.0;

        try {
            field.setAccessible(true);
            field.set(object, result);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't set currentWeight value" + "\n" + e.getMessage());
        }
    }
}
