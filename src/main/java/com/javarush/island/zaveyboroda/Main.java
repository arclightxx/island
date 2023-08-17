package com.javarush.island.zaveyboroda;

import com.javarush.island.zaveyboroda.controllers.MainController;
import com.javarush.island.zaveyboroda.gamefield.Island;
import com.javarush.island.zaveyboroda.repository.database.DataBase;
import com.javarush.island.zaveyboroda.services.utility.InitDataBase;
import com.javarush.island.zaveyboroda.view.ConsoleView;
import com.javarush.island.zaveyboroda.view.View;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = DataBase.getInstance();
        InitDataBase initDataBase = new InitDataBase();
        initDataBase.execute(dataBase);

        View view = new ConsoleView();
        MainController mainController = new MainController(dataBase, view);
        Island island = new Island(mainController);

        island.startSimulation();
    }
}

