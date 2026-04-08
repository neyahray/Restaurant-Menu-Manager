package org.example.restaurantmenumanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class PastryFXML extends HBox {

    // Твои оригинальные названия id
    @FXML private Label idPasrty;
    @FXML private Label namePastry;
    @FXML private Label pricePastry;
    @FXML private Label descriptionPastry;

    // Конструктор принимает объект твоего класса Pastry
    public PastryFXML(Pastry data) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/restaurantmenumanager/Pastry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Ошибка загрузки pastry_item.fxml", exception);
        }

        // Заполняем данными из твоего класса Pastry (используем твои геттеры)
        idPasrty.setText(String.valueOf(data.getId()));
        namePastry.setText(data.getName());
        pricePastry.setText(data.getPrice() + "€");
        descriptionPastry.setText(data.getDescription());
    }
}