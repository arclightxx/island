package com.javarush.island.zaveyboroda.repository;

import com.javarush.island.zaveyboroda.entities.Nature;

import java.util.HashMap;

public class DataBase {
    private static DataBase dataBase;
    private HashMap<String, Integer> naturesConfigMap;
    private HashMap<String, ConstantNatureFeatures> constantNaturesFeaturesMap;
    private HashMap<String, Class<? extends Nature>> natureClassesMap;

    private DataBase() {
    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public HashMap<String, Integer> getNaturesConfigMap() {
        return naturesConfigMap;
    }

    public void setNaturesConfigMap(HashMap<String, Integer> naturesConfigMap) {
        this.naturesConfigMap = naturesConfigMap;
    }

    public HashMap<String, ConstantNatureFeatures> getConstantNaturesFeaturesMap() {
        return constantNaturesFeaturesMap;
    }

    public void setConstantNaturesFeaturesMap(HashMap<String, ConstantNatureFeatures> constantNaturesFeaturesMap) {
        this.constantNaturesFeaturesMap = constantNaturesFeaturesMap;
    }

    public HashMap<String, Class<? extends Nature>> getNatureClassesMap() {
        return natureClassesMap;
    }

    public void setNatureClassesMap(HashMap<String, Class<? extends Nature>> natureClassesMap) {
        this.natureClassesMap = natureClassesMap;
    }

    //    @Override
//    public String toString() {
//    }
}
