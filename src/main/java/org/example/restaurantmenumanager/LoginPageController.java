package org.example.restaurantmenumanager;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginPageController extends CommonMethods{
    @FXML
    protected void enteredMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 1.09);
        source.setStyle("-fx-background-color: #8c8c65; -fx-background-radius: 15; -fx-cursor: hand;");
    }

    @FXML
    protected void exitedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation((Node) event.getSource(), 1.0);
        source.setStyle("-fx-background-color: #a3a380; -fx-background-radius: 15;");
    }

    @FXML
    protected void pressedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 0.9);
    }

    @FXML
    protected void releasedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 1.09);
    }

//    @FXML
//    Label notificationMessage;


//    protected void notification(String message) {
//        notificationMessage.toFront();
//        notificationMessage.setText(message);
//        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), notificationMessage);
//        fadeIn.setFromValue(0.0);
//        fadeIn.setToValue(1.0);
//
//        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), notificationMessage);
//        fadeOut.setFromValue(1.0);
//        fadeOut.setToValue(0.0);
//        fadeOut.setDelay(Duration.seconds(2));
//
//        fadeIn.play();
//        fadeIn.setOnFinished(e -> fadeOut.play());
//    }

    @FXML
    VBox choiceVBox;

    @FXML
    StackPane mainStackPane;


    protected void toSetStage(String fileName) {
        URL resource = getClass().getResource(fileName);
        if (resource == null) {
            notification("Oh, something went wrong! Please, try a little bit later");
        } else {
            try {
                Node guestPage = FXMLLoader.load(getClass().getResource(fileName));
                mainStackPane.getChildren().clear();
                mainStackPane.getChildren().add(guestPage);
            } catch (IOException e) {
                choiceVBox.setVisible(true);
                System.out.println(e.getMessage());
                notification("Oh, something went wrong! Please, try a little bit later");
            }
        }
    }

    @FXML
    VBox passwordVBox;

    @FXML
    TextField userShownField, passwordShownField;

    @FXML
    PasswordField userHiddenField, passwordHiddenField;

    @FXML
    protected void toShowUserFieldPressed(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 0.9);
        userShownField.setText(userHiddenField.getText());
        userHiddenField.setOpacity(0);
        userHiddenField.setMouseTransparent(true);
        userShownField.setOpacity(1);
        userShownField.setMouseTransparent(false);

        userShownField.requestFocus();
        userShownField.positionCaret(userShownField.getText().length());
    }

    @FXML
    protected void toShowUserFieldReleased(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 1.09);
        userHiddenField.setText(userShownField.getText());
        userShownField.setOpacity(0);
        userShownField.setMouseTransparent(true);
        userHiddenField.setOpacity(1);
        userHiddenField.setMouseTransparent(false);

        userHiddenField.requestFocus();
        userHiddenField.positionCaret(userHiddenField.getText().length());
    }

    @FXML
    protected void toShowPasswordFieldPressed(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 0.9);
        passwordShownField.setText(passwordHiddenField.getText());
        passwordHiddenField.setOpacity(0);
        passwordHiddenField.setMouseTransparent(true);
        passwordShownField.setOpacity(1);
        passwordShownField.setMouseTransparent(false);

        passwordShownField.requestFocus();
        passwordShownField.positionCaret(passwordShownField.getText().length());
    }

    @FXML
    protected void toShowPasswordFieldReleased(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 1.09);
        passwordHiddenField.setText(passwordShownField.getText());
        passwordShownField.setOpacity(0);
        passwordShownField.setMouseTransparent(true);
        passwordHiddenField.setOpacity(1);
        passwordHiddenField.setMouseTransparent(false);

        passwordHiddenField.requestFocus();
        passwordHiddenField.positionCaret(passwordHiddenField.getText().length());
    }




    protected static boolean userInputCheck(String str) {
        return str.matches("[a-zA-Z0-9_.@]+");
    }

    protected static boolean passwordInputCheck(String str) {
        return str.matches("[a-zA-Z0-9!@#$%^&*_+.,]+");
    }

    private static final HashMap<String, String> passwordsBase = new HashMap<>();
    static {
        passwordsBase.put("chef", "chef123");
        passwordsBase.put("admin", "admin123");
    }

    protected void toClearFields() {
        userShownField.clear();
        userHiddenField.clear();
        passwordShownField.clear();
        passwordHiddenField.clear();
    }

    @FXML
    protected void passwordCheck() {
        String key = userHiddenField.getText();
        String value = passwordsBase.get(key);
        String password = passwordHiddenField.getText();
        Boolean keyValid = userInputCheck(key);
        Boolean passwordValid = passwordInputCheck(password);

        if (key.isEmpty() && password.isEmpty()) {
            notification("Both username and password are empty! Enter something ");
            return;
        } else if (password.isEmpty()) {
            notification("Password field is empty! Enter something");
            return;
        } else if (key.isEmpty() ) {
            notification("Username field is empty! Enter something");
        }


        if (!keyValid && !passwordValid) {
            notification("Invalid username and password!");
            return;
        } else if (!passwordValid) {
            notification("Invalid password! Please, use only letters, digits and symbols: ! @ # $ % ^ & * _ + . ,");
            return;
        } else if (!keyValid ) {
            notification("Invalid username! Please, use only letters, digits and symbols: _ @ .");
            return;
        }

        if (!passwordsBase.containsKey(key)) {
            notification("There is no such user!");
        } else {
            if (value.equals(password)) {
                if (key.equals("chef")) {
                    currentUserRole = UserRole.CHEF;
                    toClearFields();
                    notification("Welcome! You entered as: chef");
                    toSetStage( "menu-page.fxml");
                } else if (key.equals("admin")) {
                    currentUserRole = UserRole.ADMIN;
                    toClearFields();
                    notification("Welcome! You entered as: admin");
                    toSetStage( "menu-page.fxml");
                }
            } else {
                notification("Wrong password!");
            }
        }
    }

    @FXML
    protected void loginButtonPressed(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 0.9);
        passwordCheck();
    }

    @FXML
    protected void loginButtonReleased(MouseEvent event) {
        Node source = (Node) event.getSource();
        playScaleAnimation(source, 1.04);
    }

    @FXML
    protected void passwordOnAction() {
        userShownField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordCheck();
            }
        });
        userHiddenField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordCheck();
            }
        });
        passwordShownField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordCheck();
            }
        });
        passwordHiddenField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordCheck();
            }
        });

    }

    @FXML
    Button goBackButton;

    @FXML
    VBox loginVBox;
    private List<Node> menuItems = new ArrayList<>();


    @FXML
    public void initialize() {
        menuItems.addAll(mainStackPane.getChildren());
        Connection conn = DataBaseHandler.getConnection();
        System.out.println(conn);
    }

    @FXML
    protected void goBackButtonOnAction() {
        toClearFields();
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().addAll(menuItems);
        choiceVBox.setVisible(true);
        passwordVBox.setVisible(false);
        goBackButton.setVisible(false);
    }

    @FXML
    protected void passwordPage() {
        goBackButton.toFront();
        goBackButton.setVisible(true);
        choiceVBox.setVisible(false);
        passwordVBox.setVisible(true);
    }

    @FXML
    protected void clickedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();
        switch (id) {
            case "guest":
                notification("Welcome! You entered as: guest");
                currentUserRole = UserRole.GUEST;
                toSetStage("menu-page.fxml");
                goBackButton.setVisible(true);
                break;
            case "employee":
                passwordPage();
                goBackButton.setVisible(true);
                break;
        }

    }




}
