package com.javarush.island.zaveyboroda.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONParse {
    public JSONParse() {

    }

    public static <T> T parseJSONFile(ObjectMapper mapper, String filePath, TypeReference<T> typeRef) {
        File jsonFile = new File(filePath);

        try {
            return mapper.readValue(jsonFile, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't parse " + jsonFile.getName() + "\n" + e.getMessage());
        }
    }
}
