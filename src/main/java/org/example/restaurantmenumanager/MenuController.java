package org.example.restaurantmenumanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuController extends CommonMethods{
    public void initialize() {
        switch (currentUserRole) {
            case GUEST -> {
                loadDataFromDB();
            }
            case CHEF -> {

            }
            case ADMIN -> {
                System.out.println("fv");
            }
        }
    }


    @FXML
    VBox VBoxPastry;

    protected void loadDataFromDB() {
        List<Pastry> pastriesFromDB = DataBaseHandler.getAllPastries();
        System.out.println("Найдено в базе: " + pastriesFromDB.size()); // Проверка, что база не пустая

        VBoxPastry.getChildren().clear();

        for (Pastry data : pastriesFromDB) {
            System.out.println("Добавляю: " + data.getName()); // Проверка, что цикл идет
            PastryFXML itemUI = new PastryFXML(data);
            VBoxPastry.getChildren().add(itemUI);
        }
    }




}
