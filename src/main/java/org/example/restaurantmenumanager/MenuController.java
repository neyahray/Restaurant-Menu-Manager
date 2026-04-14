package org.example.restaurantmenumanager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;
import javax.xml.transform.Source;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController extends CommonMethods{
    public void initialize() {
        switch (currentUserRole) {
            case GUEST -> {
                loadDataFromDB();
                pastryButton.fire();
            }
            case CHEF -> {

            }
            case ADMIN -> {
                System.out.println("fv");
            }
        }

        enterKBoard(toAddNameTextField, () -> toAddTextFieldOnAction(toAddNameTextField));
        enterKBoard(toAddPriceTextField, () -> toAddTextFieldOnAction(toAddPriceTextField));
        enterKBoard(toAddDescriptionTextField, () -> toAddTextFieldOnAction(toAddDescriptionTextField));


    }

    protected void setTextHighlight (String str) {
        switch (currentPastryOrDrink) {
            case pastryOrDrink.PASTRY -> {
                toAddHighlightLabel.setText("Enter " + str + " for pastry: ");
            }
            case pastryOrDrink.DRINK -> {
                toAddHighlightLabel.setText("Enter " + str + " for drink: ");
            }
        }
    }

    @FXML
    Button pastryButton, drinkButton;

    @FXML
    ScrollPane pastryScrollPane, drinkScrollPane;

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


    @FXML
    VBox VBoxPastry, VBoxDrink;

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
    Pane blackPane;

    @FXML
    StackPane crudStackPane;

    @FXML
    protected void editMenuButtonOnAction() {
        blackPane.setVisible(true);
        crudStackPane.setVisible(true);
        notificationMessage.setStyle("-fx-text-fill:  #fefaf1");
    }

    @FXML
    protected void xClicked() {
        choseOperationVBox.setVisible(true);
        toAddMain.setVisible(false);
        toAddVBox1.setVisible(true);
        toAddVBox2.setVisible(false);
        toAddVBox3.setVisible(false);
        crudStackPane.setVisible(false);
        blackPane.setVisible(false);
        notificationMessage.setStyle("-fx-text-fill:  #fefaf1");
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
    StackPane toAddMain;

    @FXML
    VBox choseOperationVBox, toAddVBox1, toAddVBox2, toAddVBox3;

    @FXML
    Label toAddHighlightLabel;

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
                break;
            case "toDeleteButton":
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



    @FXML
    TextField toAddNameTextField, toAddPriceTextField, toAddDescriptionTextField;

    @FXML
    Button backButton, nextButton;

    protected static boolean inputCheckString(String str) {
        return str.matches("[a-zA-Z0-9! ]+");
    }

    protected static boolean inputCheckDouble(String str) {
        return str.matches("[0-9.]+");
    }

    protected boolean enteredInputCheck(String input, String type) {
        if (input.isEmpty() || input == null) {
            notification("Field is empty! Enter something!");
            return false;
        }

        if (type.equals("text")) {
            if (!inputCheckString(input)) {
                notification("Invalid text!");
                return false;
            }
        } else if (type.equals("number")) {
            if (!inputCheckDouble(input)) {
                notification("Invalid price format!");
                return false;
            }
        }

        return true;
    }


    @FXML
    protected void toAddTextFieldOnAction(TextField source) {
        String id = source.getId();
        switch (id) {
            case "toAddNameTextField":
                enteredInputCheck(toAddNameTextField.getText(), "text");
                break;
            case "toAddPriceTextField":
                enteredInputCheck(toAddPriceTextField.getText(), "number");
                break;
            case "toAddDescriptionTextField":
                enteredInputCheck(toAddDescriptionTextField.getText(), "text");
                break;
        }
    }


    private void checkCurrentTextField(TextField textField, String type) {
        enteredInputCheck(textField.getText(), type);
    }

//    protected void changeTextFields(TextField textField, String curTextField) {
//        if (!enteredInputCheck()) {
//            notification("Try again!");
//        } else {
//            currentToAddInputPage = toAddInputPage.;
//            toAddNameTextField.setVisible(false);
//            toAddPriceTextField.setVisible(true);
//        }
//    }

    private String tempName;
    private double tempPrice;
    private String tempDescription;

    protected void toClearTextField(TextField textField) {
        textField.clear();
    }

    @FXML
    Label notificationMessage, toAddDoneLabel;

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
                            toClearTextField(toAddNameTextField);
                            setTextHighlight("price");
                            currentToAddInputPage = toAddInputPage.inputPrice;
                            toAddNameTextField.setVisible(false);
                            toAddPriceTextField.setVisible(true);
                        }
                        break;
                    }
                    case toAddInputPage.inputPrice -> {
                        if (enteredInputCheck(toAddPriceTextField.getText(), "number")) {
                            tempPrice = Double.parseDouble(toAddPriceTextField.getText());
                            toClearTextField(toAddPriceTextField);
                            setTextHighlight("description");
                            currentToAddInputPage = toAddInputPage.inputDescription;
                            toAddPriceTextField.setVisible(false);
                            toAddDescriptionTextField.setVisible(true);
                        }
                        break;
                    }
                    case toAddInputPage.inputDescription -> {
                        if (enteredInputCheck(toAddDescriptionTextField.getText(), "text")) {
                            tempDescription = toAddDescriptionTextField.getText();
                            toClearTextField(toAddDescriptionTextField);
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
                            currentToAddInputPage = toAddInputPage.inputName;

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
                            toAddVBox3.setVisible(true);


                        }
                        break;
                    }
                }
                break;

            case "backButton":
                break;

        }
    }



}
