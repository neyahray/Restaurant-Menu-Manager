module org.example.restaurantmenumanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;


    opens org.example.restaurantmenumanager to javafx.fxml;
    exports org.example.restaurantmenumanager;
}