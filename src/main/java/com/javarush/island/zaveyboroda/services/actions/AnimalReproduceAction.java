package com.javarush.island.zaveyboroda.services.actions;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.AnimalFeatures;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.NatureAbstractClass;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.database.DataBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalReproduceAction implements Runnable {
    private final MainController controller;
    private final AnimalFeatures animal;
    private final Island.Cell currentLocation;

    public AnimalReproduceAction(MainController controller, AnimalFeatures animal, Island.Cell currentLocation) {
        this.controller = controller;
        this.animal = animal;
        this.currentLocation = currentLocation;
    }

    @Override
    public void run() {
        synchronized (currentLocation){
            if (!animal.isAlive()) {
                return;
            }

            DataBase dataBase = controller.getDataBase();
            HashMap<String, HashSet<Nature>> natureOnCell = currentLocation.getNatureOnCell();
            HashSet<Nature> currTypeNature = natureOnCell.get(animal.getTYPE_NAME());

            if (currTypeNature.size() > animal.getMAX_AMOUNT_ON_CELL()) {
//                System.out.println(animal.getUNIQUE_NAME() + " can't reproduce: "
//                        + "Cell [" + currentLocation.getX() + "," + currentLocation.getY() + "] is full of " + animal.getTYPE_NAME() + " species");
                return;
            }

            Set<AnimalFeatures> currTypeAnimals = currTypeNature.stream()
                    .filter(animal -> animal instanceof AnimalFeatures)
                    .map(animal -> (AnimalFeatures) animal)
                    .collect(Collectors.toSet());

            Optional<AnimalFeatures> optionalAnimal = currTypeAnimals.stream()
                    .filter(NatureAbstractClass::isAlive)
                    .filter(animal -> animal.getCurrentAge() > animal.getMAX_AGE() / 2)
                    .filter(partner -> !animal.getGENDER().equals(partner.getGENDER()))
                    .findFirst();

            if (optionalAnimal.isPresent()) {
                AnimalFeatures baby = NatureFactory.createNature(animal.getClass(), dataBase, currentLocation, true);
                currentLocation.tryAddNature(baby);

//            System.out.println("[" + animal.getGENDER() + " " + animal.getUNIQUE_NAME() + " " + animal.getCurrentAge() + "y.o.] and ["
//                    + animal.getGENDER() + " " + animal.getUNIQUE_NAME() + " " + animal.getCurrentAge() + "y.o.]"
//                    + " make a baby ["
//                    + baby.getGENDER() + " " + baby.getUNIQUE_NAME() + " " + baby.getCurrentAge() + "y.o.]!!");
                NatureAbstractClass.bornCounter++;
            } else {
//            System.out.println(getUNIQUE_NAME() + " couldn't reproduce");
            }
        }
    }
}
