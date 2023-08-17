package com.javarush.island.zaveyboroda.controllers;

import com.javarush.island.zaveyboroda.repository.DataBase;
import com.javarush.island.zaveyboroda.view.View;

public class MainController {
    private final DataBase dataBase;
    private final View view;

    public MainController(DataBase dataBase, View view) {
        this.dataBase = dataBase;
        this.view = view;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public View getView() {
        return view;
    }
}
