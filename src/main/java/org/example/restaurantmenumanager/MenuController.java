package org.example.restaurantmenumanager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.geometry.Bounds;

public class MenuController extends CommonMethods {
    @FXML
    Label toUpdateDoneLabel, toUpdateHighlight, toAddHighlightLabel, notificationMessage, toAddDoneLabel,
          toDeleteChoiceLabel, toDeleteDoneLabel;

    @FXML
    Button pastryButton, drinkButton, editMenuButton, goBackButton, backButton, nextButton,
           toDeleteNoButton, toDeleteYesButton, toDeleteButton;

    @FXML
    VBox VBoxPastry, VBoxDrink, choseOperationVBox, toAddVBox1, toAddVBox2, toDeleteVBox1, stackPaneMenu,
         toUpdateVBox1;

    @FXML
    StackPane blackPane, crudStackPane, toAddMain, toDeleteMain, toUpdateMain;

    @FXML
    TextField toAddNameTextField, toAddPriceTextField, toAddDescriptionTextField, toUpdateNameTextField,
              toUpdatePriceTextField, toUpdateDescriptionTextField;

    @FXML
    ScrollPane pastryScrollPane, drinkScrollPane;


    private LoginPageController loginController;

    public void setLoginController(LoginPageController lc) {
        this.loginController = lc;
    }


    public void initialize() {
        switch (currentUserRole) {
            case GUEST -> {
                editMenuButton.setVisible(false);
                loadDataFromDB();
                pastryButton.fire();
                break;
            }
            case CHEF -> {
                toDeleteButton.setVisible(false);
                toUpdatePriceTextField.setVisible(false);
                toDeleteButton.setManaged(false);
                toUpdatePriceTextField.setManaged(false);

                loadDataFromDB();
                pastryButton.fire();
                break;
            }
            case ADMIN -> {
                loadDataFromDB();
                pastryButton.fire();
                break;
            }
        }
    }

    @FXML
    protected void goBackButtonClicked() {
        toDeleteButton.setVisible(true);
        toUpdatePriceTextField.setVisible(true);
        toDeleteButton.setManaged(true);
        toUpdatePriceTextField.setManaged(true);
    }

    protected void setTextHighlight (String str) {
        switch (currentPastryOrDrink) {
            case pastryOrDrink.PASTRY -> {
                toAddHighlightLabel.setText("Enter " + str + " for pastry: ");
                break;
            }
            case pastryOrDrink.DRINK -> {
                toAddHighlightLabel.setText("Enter " + str + " for drink: ");
                break;
            }
        }
    }

    @FXML
    private StackPane root;

    private void setBlackPaneHole(boolean hasHole) {
        blackPane.setOpacity(1.0);
        blackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.35);");

        if (!hasHole) {
            blackPane.setClip(null);
            return;
        }

        Bounds menuBounds = stackPaneMenu.localToScene(stackPaneMenu.getBoundsInLocal());
        Bounds rootBounds = root.getBoundsInLocal();

        Rectangle fullRect = new Rectangle(0, 0, rootBounds.getWidth(), rootBounds.getHeight());


        double topCrop = 30;

        Rectangle hole = new Rectangle(
                menuBounds.getMinX(),
                menuBounds.getMinY(),
                menuBounds.getWidth(),
                menuBounds.getHeight() - topCrop
        );

        hole.setLayoutX(212.5);
        hole.setLayoutY(190);
        hole.setArcWidth(35);
        hole.setArcHeight(35);

        Shape clip = Shape.subtract(fullRect, hole);
        blackPane.setClip(clip);
    }


    protected void itemButtonOnAction(Button active, Button nonActive, ScrollPane scActive, ScrollPane scNonActive) {
        nonActive.setPadding(new Insets(12, 160, 12, 160 ));
        nonActive.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.2); -fx-background-color: #a3a380; -fx-background-radius: 18; -fx-text-fill: #73734b");
        scNonActive.setVisible(false);

        active.setPadding(new Insets(15, 230, 15, 230));
        active.setStyle("-fx-text-fill: rgba(0, 0, 0, 0); -fx-background-color: #8c8c65; -fx-background-radius: 18; -fx-text-fill: #faf7f0; ");
        scActive.setVisible(true);
    }



    @FXML
    protected void pastryButtonOnAction() {
        itemButtonOnAction(pastryButton, drinkButton, pastryScrollPane, drinkScrollPane);
    }

    @FXML
    protected void drinkButtonOnAction() {
        itemButtonOnAction(drinkButton, pastryButton, drinkScrollPane, pastryScrollPane);
    }


    private void fillVBox(VBox container, List<? extends Item> items) {
        for (Item data : items) {
            container.getChildren().add(new CommonStyledItem(data));
        }
    }

    protected void loadDataFromDB() {
        VBoxPastry.getChildren().clear();
        VBoxDrink.getChildren().clear();
        fillVBox(VBoxPastry, DataBaseHandler.getAllItems("pastries"));
        fillVBox(VBoxDrink, DataBaseHandler.getAllItems("drinks"));
    }



    @FXML
    protected void editMenuButtonOnAction() {
        blackPane.setVisible(true);
        crudStackPane.setVisible(true);
        loginController.goBackButton.setVisible(false);
    }

    @FXML
    protected void xClicked() {
        notificationMessage.setTranslateY(0);

        crudStackPane.setVisible(false);
        choseOperationVBox.setVisible(true);

        toAddMain.setVisible(false);
        toAddVBox1.setVisible(true);
        toAddVBox2.setVisible(false);
        toAddDoneLabel.setVisible(false);
        blackPane.setVisible(false);

        setDeleteMode(false);
        toDeleteMain.setVisible(false);
        toDeleteVBox1.setVisible(true);
        toDeleteDoneLabel.setVisible(false);

        setUpdatePencil(false);
        toUpdateMain.setVisible(false);
        toUpdateVBox1.setVisible(true);
        toUpdateDoneLabel.setVisible(false);

        editMenuButton.setVisible(true);
        editMenuButton.setMouseTransparent(false);

        loginController.goBackButton.setVisible(true);
    }

    @FXML
    protected void crudButtonEntered(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonEntered(source, "#ffebe9", 18, "hand");
    }

    @FXML
    protected void crudButtonExited(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonExited(source, "#fff0e9", 18);
    }

    @FXML
    protected void crudButtonPressed(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonPressed(source);
    }

    @FXML
    protected void crudButtonReleased(MouseEvent event) {
        Node source = (Node) event.getSource();
        buttonPressed(source);
    }

    protected enum toAddInputPage {
        inputName,
        inputPrice,
        inputDescription,
        none
    }

    protected static toAddInputPage currentToAddInputPage = toAddInputPage.none;



    @FXML
    protected void crudButtonClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();

        switch (id) {
            case "toAddButton":
                choseOperationVBox.setVisible(false);
                toAddMain.setVisible(true);
                toAddVBox1.setVisible(true);
                currentToAddInputPage = toAddInputPage.inputName;
                break;
            case "toUpdateButton":
                crudStackPane.setVisible(false);
                choseOperationVBox.setVisible(false);
                setBlackPaneHole(true);
                setUpdatePencil(true);
                toUpdateMain.setVisible(true);
                notificationMessage.setTranslateY(-20);
                break;
            case "toDeleteButton":
                crudStackPane.setVisible(false);
                choseOperationVBox.setVisible(false);
                setBlackPaneHole(true);
                setDeleteMode(true);
                toDeleteMain.setVisible(false);
                break;
        }
    }



    protected enum pastryOrDrink {
        PASTRY,
        DRINK,
        none
    }

    protected static pastryOrDrink currentPastryOrDrink = pastryOrDrink.none;

    @FXML
    protected void choiceItemButtonClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();
        switch (id) {
            case "choicePastryButton":
                currentPastryOrDrink = pastryOrDrink.PASTRY;
                break;
            case "choiceDrinkButton":
                currentPastryOrDrink = pastryOrDrink.DRINK;
                break;

        }
        toAddVBox1.setVisible(false);
        toAddVBox2.setVisible(true);
        currentToAddInputPage = toAddInputPage.inputName;

        setTextHighlight("name");
    }



    protected static boolean inputCheckString(String str) {
        return str.matches("[a-zA-Z0-9!' -]+");
    }

    protected static boolean inputCheckDouble(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }


    protected boolean enteredInputCheck(String input, String type) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        if (type.equals("text")) {
            if (!inputCheckString(input)) {
                return false;
            }
        } else if (type.equals("number")) {
            if (!inputCheckDouble(input)) {
                return false;
            }
        }

        return true;
    }

    protected void enteredInputCheckToPrint(TextField textFieldName, TextField textFieldPrice, TextField textFieldDescription) {
        String name = textFieldName.getText();
        String price = String.valueOf(textFieldPrice.getText());
        String description = textFieldDescription.getText();

        if (name.isEmpty() && price.isEmpty() && description.isEmpty()) {
            notification("All three fields are empty! Enter something");
            return;
        } else if (name.isEmpty() && price.isEmpty()) {
            notification("Enter name and price!");
            return;
        } else if (name.isEmpty() && description.isEmpty()) {
            notification("Enter name and description!");
            return;
        } else if (price.isEmpty() && description.isEmpty()) {
            notification("Enter price and description!");
            return;
        } else if (name.isEmpty()) {
            notification("Enter name!");
            return;
        } else if (price.isEmpty()) {
            notification("Enter price!");
            return;
        } else if (description.isEmpty()) {
            notification("Enter description!");
            return;
        }


        if (!inputCheckString(name) && !inputCheckDouble(price) && !inputCheckString(description)) {
            notification("All three fields are invalid!");
            return;
        } else if (!inputCheckString(name) && !inputCheckDouble(price)) {
            notification("Name and price are invalid!");
            return;
        } else if (!inputCheckString(name) && !inputCheckString(description)) {
            notification("Name and description are invalid!");
            return;
        } else if (!inputCheckDouble(price) && !inputCheckString(description)) {
            notification("Price and description are invalid!");
            return;
        } else if (!inputCheckString(name)) {
            notification("Name is invalid!");
            return;
        } else if (!inputCheckDouble(price)) {
            notification("Price is invalid!");
            return;
        } else if (!inputCheckString(description)) {
            notification("Description is invalid!");
            return;
        }

        return;

    }


    protected void enteredInputCheckToPrintForAdd() {
        String input = "";
        String type = "";
        String fieldName = "";

        if (toAddNameTextField.isVisible()) {
            input = toAddNameTextField.getText();
            type = "text";
            fieldName = "name";
        } else if (toAddPriceTextField.isVisible()) {
            input = toAddPriceTextField.getText();
            type = "number";
            fieldName = "price";
        } else if (toAddDescriptionTextField.isVisible()) {
            input = toAddDescriptionTextField.getText();
            type = "text";
            fieldName = "description";
        }

        if (input == null || input.trim().isEmpty()) {
            notification("Enter " + fieldName + "!");
            return;
        }

        if (type.equals("text") && !inputCheckString(input)) {
            notification("Invalid " + fieldName + "!");
            return;
        }

        if (type.equals("number") && !inputCheckDouble(input)) {
            notification("Invalid " + fieldName + "!");
            return;
        }
    }

    private String tempName;
    private double tempPrice;
    private String tempDescription;
    private CommonStyledItem currentDeleteItem;
    private CommonStyledItem currentUpdateItem;

    @FXML
    protected void BackNextButtonClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();

        switch (id) {
            case "nextButton":
                switch (currentToAddInputPage) {
                    case toAddInputPage.inputName -> {
                        if (enteredInputCheck(toAddNameTextField.getText(), "text")) {
                            tempName = toAddNameTextField.getText();
                            currentToAddInputPage = toAddInputPage.inputPrice;
                            toAddNameTextField.clear();
                            setTextHighlight("price");
                            toAddNameTextField.setVisible(false);
                            toAddPriceTextField.setVisible(true);
                        } else {
                            enteredInputCheckToPrintForAdd();
                        }
                        break;
                    }
                    case toAddInputPage.inputPrice -> {
                        if (enteredInputCheck(toAddPriceTextField.getText(), "number")) {
                            tempPrice = Double.parseDouble(toAddPriceTextField.getText());
                            currentToAddInputPage = toAddInputPage.inputDescription;
                            toAddPriceTextField.clear();
                            setTextHighlight("description");
                            toAddPriceTextField.setVisible(false);
                            toAddDescriptionTextField.setVisible(true);
                        } else {
                            enteredInputCheckToPrintForAdd();
                        }

                        break;
                    }
                    case toAddInputPage.inputDescription -> {
                        if (enteredInputCheck(toAddDescriptionTextField.getText(), "text")) {
                            tempDescription = toAddDescriptionTextField.getText();
                            toAddDescriptionTextField.clear();
                            setTextHighlight("name");

                            Item newitem = null;
                            String table = "";
                            switch (currentPastryOrDrink) {
                                case PASTRY -> {
                                    newitem = new Pastry(tempName, tempPrice, tempDescription);
                                    table = "pastries";
                                }
                                case DRINK -> {
                                    newitem = new Drink(tempName, tempPrice, tempDescription);
                                    table = "drinks";
                                }
                            }
                            DataBaseHandler.addItem(newitem, table);
                            loadDataFromDB();

                            switch (currentPastryOrDrink) {
                                case PASTRY -> {
                                    toAddDoneLabel.setText("New pastry '" + tempName + "' added! Check menu");
                                }
                                case DRINK -> {
                                    toAddDoneLabel.setText("New drink '" + tempName + "' added! Check menu");
                                }
                            }

                            toAddVBox2.setVisible(false);
                            toAddDescriptionTextField.setVisible(false);
                            toAddNameTextField.setVisible(true);
                            toAddDoneLabel.setVisible(true);
                        } else {
                            enteredInputCheckToPrintForAdd();
                        }
                        break;
                    }
                }
                break;
            case "backButton":
                switch (currentToAddInputPage) {
                    case toAddInputPage.inputName -> {
                        toAddNameTextField.clear();
                        toAddVBox2.setVisible(false);
                        toAddVBox1.setVisible(true);
                        break;
                    }
                    case toAddInputPage.inputPrice -> {
                        toAddPriceTextField.clear();
                        toAddPriceTextField.setVisible(false);
                        currentToAddInputPage = toAddInputPage.inputName;
                        toAddNameTextField.setVisible(true);
                        setTextHighlight("name");
                        break;
                    }
                    case toAddInputPage.inputDescription -> {
                        toAddDescriptionTextField.clear();
                        toAddDescriptionTextField.setVisible(false);
                        currentToAddInputPage = toAddInputPage.inputPrice;
                        toAddPriceTextField.setVisible(true);
                        setTextHighlight("price");
                        break;
                    }
                }
                break;
        }
    }






    //DELETE
    private void setDeleteMode(boolean visible) {
        editMenuButton.setMouseTransparent(true);
        for (var node : VBoxPastry.getChildren()) {
            if (node instanceof CommonStyledItem item) {
                item.getDashButton().setVisible(visible);
                item.getDashButton().setManaged(true);
                if (visible) {
                    item.getDashButton().setOnAction(e -> handleDeleteClick(item));
                }
            }
        }
        for (var node : VBoxDrink.getChildren()) {
            if (node instanceof CommonStyledItem item) {
                item.getDashButton().setVisible(visible);
                item.getDashButton().setManaged(true);
                if (visible) {
                    item.getDashButton().setOnAction(e -> handleDeleteClick(item));
                }
            }
        }
    }




    protected void handleDeleteClick(CommonStyledItem item) {
        this.currentDeleteItem = item;
        setBlackPaneHole(false);
        crudStackPane.setVisible(true);
        toDeleteMain.setVisible(true);
        toDeleteVBox1.setVisible(true);

        toDeleteChoiceLabel.setText("Are you sure, you want to delete '" + currentDeleteItem.getItem().getName() + "'?");
    }

    @FXML
    protected void toDeleteChoiceButtonClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();

        switch (id) {
            case "toDeleteYesButton":
                DataBaseHandler.deleteItem(currentDeleteItem.getItem().getId(), currentDeleteItem.getItem().getTableName());
                VBoxPastry.getChildren().remove(currentDeleteItem);
                VBoxDrink.getChildren().remove(currentDeleteItem);
                toDeleteVBox1.setVisible(false);
                toDeleteDoneLabel.setVisible(true);
                toDeleteDoneLabel.setText("'" + currentDeleteItem.getItem().getName() + "' deleted! Check Menu");
                setDeleteMode(false);
                break;
            case "toDeleteNoButton":
                xClicked();
                break;
        }
    }



    //UPDATE
    private void setUpdatePencil(boolean visible) {
        editMenuButton.setMouseTransparent(true);
        for (var node : VBoxPastry.getChildren()) {
            if (node instanceof CommonStyledItem item) {
                item.getUpdatePencil().setVisible(visible);
                item.getUpdatePencil().setManaged(true);

                if (visible) {
                    item.getUpdatePencil().setOnAction(e -> handleUpdateClick(item));
                }
            }
        }
        for (var node : VBoxDrink.getChildren()) {
            if (node instanceof CommonStyledItem item) {
                item.getUpdatePencil().setVisible(visible);
                item.getDashButton().setManaged(true);

                if (visible) {
                    item.getUpdatePencil().setOnAction(e -> handleUpdateClick(item));
                }
            }
        }

    }


    protected void handleUpdateClick(CommonStyledItem updateItem) {
            this.currentUpdateItem = updateItem;
            setBlackPaneHole(false);
            crudStackPane.setVisible(true);
            toUpdateMain.setVisible(true);
            toUpdateVBox1.setVisible(true);

            Item data = currentUpdateItem.getItem();
            toUpdateNameTextField.setText(data.getName());
            toUpdatePriceTextField.setText(Double.toString(data.getPrice()));
            toUpdateDescriptionTextField.setText(data.getDescription());

            toUpdateHighlight.setText("Updating '" + data.getName() + "'");
    }

    @FXML
    protected void UpdateBackNextButtonClicked(MouseEvent event) {
        Node source = (Node) event.getSource();
        String id = source.getId();
        switch (id) {
            case "backUpdateButton":
                xClicked();
                break;
            case "nextUpdateButton":
                if (enteredInputCheck(toUpdateNameTextField.getText(), "text") &&
                        enteredInputCheck(toUpdatePriceTextField.getText(), "number") &&
                        enteredInputCheck(toUpdateDescriptionTextField.getText(), "text")) {
                    DataBaseHandler.updateItem(currentUpdateItem.getItem().getId(), toUpdateNameTextField.getText(), Double.parseDouble(toUpdatePriceTextField.getText()), toUpdateDescriptionTextField.getText(), currentUpdateItem.getItem().getTableName());
                    toUpdateVBox1.setVisible(false);
                    toUpdateDoneLabel.setVisible(true);
                    toUpdateDoneLabel.setText("'" + currentUpdateItem.getItem().getName() + "' is updated! Check Menu");
                    loadDataFromDB();
                } else {
                    enteredInputCheckToPrint(toUpdateNameTextField, toUpdatePriceTextField, toUpdateDescriptionTextField);

                }
                break;

        }
    }

}
