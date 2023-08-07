package com.javarush.island.zaveyboroda;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.factory.NatureFactory;
import com.javarush.island.zaveyboroda.repository.ConstantFilePath;
import com.javarush.island.zaveyboroda.repository.ConstantNatureClassesPath;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DataBase;
import com.javarush.island.zaveyboroda.services.JSONParse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DataBase db = initDataBase();

        initNaturesOnCell(db);
        System.out.println();
        initNaturesOnCell(db);
        System.out.println();
        initNaturesOnCell(db);
    }

    private static void initNaturesOnCell(DataBase db) {
        HashMap<String, HashSet<Nature>> naturesOnCellAmount = new HashMap<>();

        for (String key: db.getNatureClassesMap().keySet()) {
            Class<? extends Nature> natureClass = db.getNatureClassesMap().get(key);

            if (db.getNaturesConfigMap().get(key) == 0) {
                naturesOnCellAmount.put(key, new HashSet<>());
            }

            HashSet<Nature> naturesSet = new HashSet<>();
            for (int i = 0; i < db.getNaturesConfigMap().get(key); i++){
                Nature natureObject = NatureFactory.createNature(natureClass, db);
                naturesSet.add(natureObject);
                naturesOnCellAmount.put(key, naturesSet);
            }
        }

        naturesOnCellAmount.forEach((natureName, natureObject) -> System.out.println(natureName + " " + natureObject.size()));
    }

    private static DataBase initDataBase() {
        ObjectMapper mapper = getAndActivateObjectMapper();

        JSONParse jsonParse = JSONParse.getInstance();
        DataBase dataBase = DataBase.getInstance();

        HashMap<String, Integer> configMap = jsonParse.parseJSONFile(mapper, ConstantFilePath.NATURES_CONFIG_PATH, new TypeReference<HashMap<String, Integer>>() {});
        dataBase.setNaturesConfigMap(configMap);

        HashMap<String, ConstantNatureFeatures> constantFeaturesMap = jsonParse.parseJSONFile(mapper, ConstantFilePath.CONSTANT_NATURES_FEATURES_PATH, new TypeReference<HashMap<String, ConstantNatureFeatures>>() {});
        dataBase.setConstantNaturesFeaturesMap(constantFeaturesMap);

        Set<String> natureSet = configMap.keySet();
        HashMap<String, Class<? extends Nature>> natureClasses = new HashMap<>();
        fillNatureClassesMap(natureSet, natureClasses);
        dataBase.setNatureClassesMap(natureClasses);

        return dataBase;
    }

    private static ObjectMapper getAndActivateObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private static void fillNatureClassesMap(Set<String> natureSet, HashMap<String, Class<? extends Nature>> natureClasses) {
        for (String natureName : natureSet) {
            try {
                Class<?> natureClass = ConstantNatureClassesPath.classFinder(natureName);
                natureClasses.put(natureName, (Class<? extends Nature>) natureClass);
            } catch (ClassNotFoundException e) {
                System.err.println("Class " + natureName + " not found");
            }
        }
    }
}

