package org.example.restaurantmenumanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CommonStyledItem extends HBox {
    private Item item;

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
            throw new RuntimeException(exception);
        }

        this.item = item;

        id.setText("❀");
        name.setText(item.getName());
        price.setText(item.getPrice() + "$");
        description.setText(item.getDescription());
    }

    @FXML
    Button deleteDash, updatePencil;

    public Button getDashButton() {
        deleteDash.setManaged(false);
        return deleteDash;
    }

    public Button getUpdatePencil() {
        updatePencil.setManaged(false);
        return updatePencil;
    }

    public Item getItem() {
        return item;
    }


}