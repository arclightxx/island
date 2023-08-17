package com.javarush.island.zaveyboroda.view;

import com.javarush.island.zaveyboroda.entities.Nature;

import java.util.List;

public interface View {
    void displayState(int i, List<Nature> allNatureList);
    void dayZeroDisplayState(int natureSize);
}
