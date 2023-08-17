package com.javarush.island.zaveyboroda.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.island.zaveyboroda.entities.Nature;
import com.javarush.island.zaveyboroda.repository.ConstantFilePath;
import com.javarush.island.zaveyboroda.repository.ConstantNatureClassesPath;
import com.javarush.island.zaveyboroda.repository.ConstantNatureFeatures;
import com.javarush.island.zaveyboroda.repository.DataBase;

import java.util.HashMap;
import java.util.Set;

public class InitDataBase {
    public void execute(DataBase dataBase) {
        ObjectMapper mapper = getAndActivateObjectMapper();

        parseAndSetConfigMap(mapper, dataBase);

        parseAndSetConstantFeaturesMap(mapper, dataBase);

        setNatureClassesMap(dataBase);

        parseAndSetPreferableFoodMap(mapper, dataBase);
    }

    private ObjectMapper getAndActivateObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    private void parseAndSetConfigMap(ObjectMapper mapper, DataBase dataBase) {
        HashMap<String, Integer> configMap = JSONParse.parseJSONFile(mapper,
                ConstantFilePath.NATURES_CONFIG_PATH,
                new TypeReference<HashMap<String, Integer>>() {});

        dataBase.setNaturesConfigMap(configMap);
    }

    private void parseAndSetConstantFeaturesMap(ObjectMapper mapper, DataBase dataBase) {
        HashMap<String, ConstantNatureFeatures> constantFeaturesMap = JSONParse.parseJSONFile(mapper,
                ConstantFilePath.CONSTANT_NATURES_FEATURES_PATH,
                new TypeReference<HashMap<String, ConstantNatureFeatures>>() {});

        dataBase.setConstantNaturesFeaturesMap(constantFeaturesMap);
    }

    private void setNatureClassesMap(DataBase dataBase) {
        Set<String> natureSet = dataBase.getNaturesConfigMap().keySet();
        HashMap<String, Class<Nature>> natureClasses = new HashMap<>();

        fillNatureClassesMap(natureSet, natureClasses);

        dataBase.setNatureClassesMap(natureClasses);
    }

    private void fillNatureClassesMap(Set<String> natureSet, HashMap<String, Class<Nature>> natureClasses) {
        for (String natureName : natureSet) {
            try {
                Class<?> natureClass = ConstantNatureClassesPath.classFinder(natureName);
                natureClasses.put(natureName, (Class<Nature>) natureClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + natureName + " not found");
            }
        }
    }

    private void parseAndSetPreferableFoodMap(ObjectMapper mapper, DataBase dataBase) {
        HashMap<String, HashMap<String, Integer>> prefFoodMap = JSONParse.parseJSONFile(mapper,
                ConstantFilePath.PREFERABLE_FOOD_PATH,
                new TypeReference<HashMap<String, HashMap<String, Integer>>>() {});

        dataBase.setPreferableFoodMap(prefFoodMap);
    }
}
