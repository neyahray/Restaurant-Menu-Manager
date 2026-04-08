module org.example.restaurantmenumanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;


    opens org.example.restaurantmenumanager to javafx.fxml;
    exports org.example.restaurantmenumanager;
}