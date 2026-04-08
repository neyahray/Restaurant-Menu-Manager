package org.example.restaurantmenumanager;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CommonMethods {
    public enum UserRole {
        GUEST,
        CHEF,
        ADMIN
    }

    public static UserRole currentUserRole = UserRole.GUEST;

    protected void playScaleAnimation(Node node, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }

    @FXML
    protected Label notificationMessage;

    protected void notification(String message) {
        notificationMessage.toFront();
        notificationMessage.setText(message);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), notificationMessage);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), notificationMessage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(2));

        fadeIn.play();
        fadeIn.setOnFinished(e -> fadeOut.play());
    }




}
