package com.javarush.island.zaveyboroda.services;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.NatureAbstractClass;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.DataBase;
import com.javarush.island.zaveyboroda.repository.DeadCause;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class EatAction implements Runnable {
    private final MainController controller;
    private final AnimalFeatures animal;
    private final Island.Cell currentLocation;

    public EatAction(MainController controller, AnimalFeatures animal, Island.Cell currentLocation) {
        this.controller = controller;
        this.animal = animal;
        this.currentLocation = currentLocation;
    }

    @Override
    public void run() {
        synchronized (currentLocation) {
            if (!animal.isAlive()) {
                return;
            }

            DataBase dataBase = controller.getDataBase();
            HashMap<String, HashMap<String, Integer>> preferableFood = dataBase.getPreferableFoodMap();
            HashMap<String, Integer> currAnimalPrefFood = preferableFood.get(animal.getTYPE_NAME());
            HashMap<String, HashSet<Nature>> natureOnCell = currentLocation.getNatureOnCell();

            Optional<Nature> optionalNatureToEat = natureOnCell.values().stream()
                    .flatMap(Collection::stream)
                    .filter(nature -> nature.isAlive() && canEat(nature, currAnimalPrefFood))
                    .findFirst();

            if (optionalNatureToEat.isPresent()) {
                Nature natureToEat = optionalNatureToEat.get();
                synchronized (natureToEat) {
                    if (natureToEat.isAlive()) {
                        natureToEat.die(DeadCause.HAS_BEEN_EATEN);

                        double animalWeight = calculateNewAnimalWeight(natureToEat);

                        animal.setCurrentWeight(animalWeight);
                        NatureAbstractClass.eatCounter++;

//                        System.out.println(natureToEat.getUNIQUE_NAME() + " " + DeadCause.HAS_BEEN_EATEN + " by " + animal.getUNIQUE_NAME()
//                                + " on [" + currentLocation.getX() + "," + currentLocation.getY() + "]");
                    }
                }
            } else {
//                System.out.println(animal.getUNIQUE_NAME() + " couldn't eat");
            }
        }
    }

    private boolean canEat(Nature nature, HashMap<String, Integer> currAnimalPrefFood) {
        int eatRandomChance = ThreadLocalRandom.current().nextInt(0, 100);
        String natureName = nature.getTYPE_NAME();
        int eatChanceOfNature = currAnimalPrefFood.get(natureName);

        return eatRandomChance <= eatChanceOfNature && eatChanceOfNature != 0;
    }

    private double calculateNewAnimalWeight(Nature natureToEat) {
        double animalWeight = animal.getCurrentWeight();
        double foodWeight = natureToEat.getCurrentWeight();
        double deltaWeight = Math.min(foodWeight, animal.getAMOUNT_OF_FOOD_TO_FILL());
        animalWeight += deltaWeight;
        
        return animalWeight;
    }
}
