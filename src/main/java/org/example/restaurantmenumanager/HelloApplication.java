package org.example.restaurantmenumanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DataBaseHandler.createTables();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        stage.setTitle("'Spring' Cafe Menu ❀");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/floralIcon.png")));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}