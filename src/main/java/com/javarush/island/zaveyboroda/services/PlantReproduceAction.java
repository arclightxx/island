package com.javarush.island.zaveyboroda.services;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.NatureAbstractClass;
import com.javarush.island.zaveyboroda.entities.plants.PlantFeatures;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.DataBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class PlantReproduceAction implements Runnable {
    private final MainController controller;
    private final PlantFeatures plant;
    private final Island.Cell currentLocation;

    public PlantReproduceAction(MainController controller, PlantFeatures plant, Island.Cell currentLocation) {
        this.controller = controller;
        this.plant = plant;
        this.currentLocation = currentLocation;
    }

    @Override
    public void run() {
        synchronized (currentLocation) {
            if (!plant.isAlive()) {
                return;
            }

            DataBase dataBase = controller.getDataBase();
            HashMap<String, HashSet<Nature>> natureOnCell = currentLocation.getNatureOnCell();
            HashSet<Nature> currTypeOfNature = natureOnCell.get(plant.getTYPE_NAME());
            boolean bornChance = ThreadLocalRandom.current().nextInt(0, 100) < 50;

            if (currTypeOfNature.size() > plant.getMAX_AMOUNT_ON_CELL()) {
//                System.out.println(plant.getUNIQUE_NAME() + " can't reproduce: "
//                        + "Cell [" + currentLocation.getX() + "," + currentLocation.getY() + "] is full of " + plant.getTYPE_NAME() + " species");
                return;
            }

            if (bornChance) {
                PlantFeatures baby = NatureFactory.createNature(plant.getClass(), dataBase, currentLocation, true);
                currentLocation.tryAddNature(baby);
                NatureAbstractClass.bornCounter++;
            } else {
//            System.out.println(getUNIQUE_NAME() + " couldn't reproduce");
            }
        }
    }
}
