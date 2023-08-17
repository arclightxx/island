package com.javarush.island.zaveyboroda.view;

import com.javarush.island.zaveyboroda.entities.Herbivore;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.entities.NatureAbstractClass;
import com.javarush.island.zaveyboroda.entities.Predator;
import com.javarush.island.zaveyboroda.entities.plants.PlantFeatures;

import java.util.List;

public class ConsoleView implements View {
    @Override
    public void displayState(int i, List<Nature> allNatureList) {
        int allNature = allNatureList.size();
        int[] amountOfEachAnimal = calculateAmountOfEachAnimal(allNatureList);
        int predators = amountOfEachAnimal[0];
        int herbivores = amountOfEachAnimal[1];
        int omnivores = amountOfEachAnimal[2];
        int plants = amountOfEachAnimal[3];

        System.out.println("Day " + (i+1) + ":");
        System.out.println("\t" + allNature + " nature left: " + predators + " Predators; "
        + herbivores + " Herbivores; " + omnivores + " Omnivores; " + plants + " Plants");

        System.out.println("\t\t" + NatureAbstractClass.dieCounter + " died naturally;");
        System.out.println("\t\t" + NatureAbstractClass.bornCounter + " has been born;");
        System.out.println("\t\t" + NatureAbstractClass.eatCounter + " has been eaten;");
        NatureAbstractClass.dieCounter = 0;
        NatureAbstractClass.bornCounter = 0;
        NatureAbstractClass.eatCounter = 0;
    }

    private int[] calculateAmountOfEachAnimal(List<Nature> allNatureList) {
        int[] array = new int[4];

        array[0] = (int) allNatureList.stream()
                .filter(nature -> nature instanceof Predator && !(nature instanceof Herbivore))
                .count();
        array[1] = (int) allNatureList.stream()
                .filter(nature -> nature instanceof Herbivore && !(nature instanceof Predator))
                .count();
        array[2] = (int) allNatureList.stream()
                .filter(nature -> nature instanceof Predator && nature instanceof Herbivore)
                .count();
        array[3] = (int) allNatureList.stream()
                .filter(nature -> nature instanceof PlantFeatures)
                .count();

        return array;
    }
}
