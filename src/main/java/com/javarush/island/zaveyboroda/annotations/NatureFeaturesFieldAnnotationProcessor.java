package com.javarush.island.zaveyboroda.annotations;

import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.Gender;

import java.lang.reflect.Field;

public class NatureFeaturesFieldAnnotationProcessor {
    private NatureFeaturesFieldAnnotationProcessor() {

    }

    public static void calculateAndSetAnnotatedFields(Object object, ConstantNatureFeatures constantNatureFeatures) {
        Class<?> clazz = object.getClass().getSuperclass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectRandomCurrentWeight.class)) {
                calculateAndSetRandomCurrentWeight(object, constantNatureFeatures, field);
            } else if (field.isAnnotationPresent(InjectRandomCurrentAge.class)) {
                CalculateRandomAgeProcessor.calculateAndSetRandomCurrentAge(object, constantNatureFeatures);
            } else if (field.isAnnotationPresent(InjectRandomGender.class)) {
                calculateAndSetGender(object, field);
            }
        }
    }

    private static void calculateAndSetRandomCurrentWeight(Object object, ConstantNatureFeatures constantNatureFeatures, Field field) {
        InjectRandomCurrentWeight weightAnnotation = field.getAnnotation(InjectRandomCurrentWeight.class);
        double MAX_WEIGHT = constantNatureFeatures.getMAX_WEIGHT();
        double min = weightAnnotation.min();
        double max = weightAnnotation.max();

        double result = MAX_WEIGHT - MAX_WEIGHT * (min + (Math.random() * (max - min)));
        result = Math.round(result * 1000.0) / 1000.0;

        try {
            field.setAccessible(true);
            field.set(object, result);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't set currentWeight value" + "\n" + e.getMessage());
        }
    }

//    private static void calculateAndSetRandomCurrentAge(Object object, ConstantNatureFeatures constantNatureFeatures) {
//        InjectRandomCurrentAge ageAnnotation = field.getAnnotation(InjectRandomCurrentAge.class);
//        int MAX_AGE = constantNatureFeatures.getMAX_AGE();
//        int min = ageAnnotation.min();
//
//        int result = random.nextInt(MAX_AGE - min) + min;
//
//        try {
//            field.setAccessible(true);
//            field.set(object, result);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException("Couldn't set currentAge value" + "\n" + e.getMessage());
//        }
//    }

    private static void calculateAndSetGender(Object object, Field field) {
        InjectRandomGender genderAnnotation = field.getAnnotation(InjectRandomGender.class);
        Gender gender = Math.random() > 0.5 ? Gender.MALE : Gender.FEMALE;

        try {
            field.setAccessible(true);
            field.set(object, gender);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't set gender value" + "\n" + e.getMessage());
        }
    }
}
