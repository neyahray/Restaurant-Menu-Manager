package org.example.restaurantmenumanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class CommonStyledItem extends HBox {

    // Твои оригинальные названия id
    @FXML private Label id;
    @FXML private Label name;
    @FXML private Label price;
    @FXML private Label description;

    public CommonStyledItem(Item item) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/restaurantmenumanager/CommonStyledItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException("Ошибка загрузки pastry_item.fxml", exception);
        }

        id.setText(item.getId() + "");
        name.setText(item.getName());
        price.setText(item.getPrice() + "$");
        description.setText(item.getDescription());

    }
}