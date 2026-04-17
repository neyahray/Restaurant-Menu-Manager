package org.example.restaurantmenumanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginPageController extends CommonMethods {
    @FXML
    Label flowerUsernameButton, flowerPasswordButton;
    @FXML
    Button goBackButton;
    @FXML
    TextField usernameTextField, passwordTextField;
    @FXML
    PasswordField usernamePasswordField, passwordPasswordField;
    @FXML
    VBox choiceVBox, passwordVBox, loginVBox;
    @FXML
    StackPane mainStackPane;

    //functional for buttons
    @FXML
    protected void enteredMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonEntered(source, "#8c8c65", 15, "hand");
    }

    @FXML
    protected void exitedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonExited(source, "#a3a380", 15);
    }

    @FXML
    protected void pressedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonPressed(source);
    }

    @FXML
    protected void releasedMouseChoiceButton(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonReleased(source);
    }


    protected void toSetStage(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            Node guestPage = loader.load();
            MenuController menuController = loader.getController();
            menuController.setLoginController(this);
            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(guestPage);
        } catch (IOException e) {
            choiceVBox.setVisible(true);
            System.out.println(e.getMessage());
            notification("Oh, something went wrong! Please, try a little bit later");
        }
    }

    protected void toShowText(TextField textField, PasswordField passwordField) {
        textField.setText(passwordField.getText());
        passwordField.setOpacity(0);
        passwordField.setMouseTransparent(true);
        textField.setOpacity(1);
        textField.setMouseTransparent(false);

        textField.requestFocus();
        textField.positionCaret(textField.getText().length());
    }

    protected void toHideText(TextField textField, PasswordField passwordField) {
        passwordField.setText(textField.getText());
        textField.setOpacity(0);
        textField.setMouseTransparent(true);
        passwordField.setOpacity(1);
        passwordField.setMouseTransparent(false);

        passwordField.requestFocus();
        passwordField.positionCaret(passwordField.getText().length());
    }

    @FXML
    protected void flowerPressed(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();
        switch (id) {
            case "flowerUsernameButton":
                playScaleAnimation(source, 0.9);
                toShowText(usernameTextField, usernamePasswordField);
                break;
            case "flowerPasswordButton":
                playScaleAnimation(source, 0.9);
                toShowText(passwordTextField, passwordPasswordField);
                break;
        }
    }

    @FXML
    protected void flowerReleased(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();
        switch (id) {
            case "flowerUsernameButton":
                playScaleAnimation(source, 1.09);
                toHideText(usernameTextField, usernamePasswordField);
                break;
            case "flowerPasswordButton":
                playScaleAnimation(source, 1.09);
                toHideText(passwordTextField, passwordPasswordField);
                break;
        }
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
        usernameTextField.clear();
        usernamePasswordField.clear();
        passwordTextField.clear();
        passwordPasswordField.clear();
    }

    @FXML
    protected void passwordCheck() {
        String key = usernamePasswordField.getText();
        String value = passwordsBase.get(key);
        String password = passwordPasswordField.getText();
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

    protected void passwordOnActionShown(TextField textField) {
        enterKBoard(textField, this::passwordCheck);
    }
    protected void passwordOnActionHidden(PasswordField passwordField) {
        enterKBoard(passwordField,this::passwordCheck);
    }

    @FXML
    protected void passwordOnAction() {
        passwordOnActionShown(usernameTextField);
        passwordOnActionHidden(usernamePasswordField);
        passwordOnActionShown(passwordTextField);
        passwordOnActionHidden(passwordPasswordField);
    }

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