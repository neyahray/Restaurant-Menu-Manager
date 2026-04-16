package org.example.restaurantmenumanager;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

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

    protected void buttonEntered(Node button, String color, int radius, String cursor) {
        playScaleAnimation(button, 1.09);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: " + radius + "; -fx-cursor: " + cursor);
    }

    protected void buttonExited(Node button, String color, int radius) {
        playScaleAnimation(button, 1);
        button.setStyle("-fx-background-color: " + color + "; -fx-background-radius: " + radius);
    }

    protected void buttonPressed(Node button) {
        playScaleAnimation(button, 0.9);
    }

    protected void buttonReleased(Node button) {
        playScaleAnimation(button, 1.09);
    }

    protected void enterKBoard(Node node, Runnable action) { //Runnable action for methods
        node.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                action.run();
            }
        });
    }
}
