package org.example.restaurantmenumanager;

import javafx.fxml.FXML;

import java.lang.classfile.Label;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseHandler extends CommonMethods{
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
                System.out.println("Connected to database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void createTables() {
        String pastriesSQL = """
            CREATE TABLE IF NOT EXISTS pastries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                description TEXT
            );
            """;

        String drinksSQL = """
                CREATE TABLE IF NOT EXISTS drinks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    price REAL NOT NULL
                );
                """;

        String insertPastriesSQL = """
                INSERT OR IGNORE INTO pastries (id, name, price, description) VALUES 
                (1, 'Matcha waffle', 80, 'Waffles with matcha'),
                (2, 'Dubai chocolate muffin', 60, 'gold labubu'),
                (3, 'Red velvet', 120, 'yummy'),
                (4, 'Blueberry doughnut', 30, 'Dariia loves it');
                """;

        String insertDrinksSQL = """
                INSERT OR IGNORE INTO drinks (id, name, price) VALUES
                (1, 'Matcha', 200),
                (2, 'Matcha-Mango', 220),
                (3, 'Birch sap', 50),
                (4, 'Apple juice', 50),
                (5, 'Orange juice', 50),
                (6, 'Apricote compote', 70),
                (7, 'Pineapple Energy drink', 70 (free for Dariia);
                """;

        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute(pastriesSQL);
            stmt.execute(drinksSQL);
            stmt.execute(insertPastriesSQL);
            stmt.execute(insertDrinksSQL);
            System.out.println("Tables created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Methods for pastries
    public static void addPastry(Pastry pastry) {
        String sql = "INSERT INTO dishes (name, price, description) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, pastry.getName());
            stmt.setDouble(2, pastry.getPrice());
            stmt.setString(3, pastry.getDescription());
            stmt.executeUpdate();
            System.out.println("Dish added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Pastry> getAllPastries() {
        ArrayList<Pastry> pastries = new ArrayList<>();
        String sql = "SELECT * FROM pastries";

        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");

                Pastry pastry = new Pastry(id, name, price, description);
                pastries.add(pastry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pastries;
    }



    //Methods for drinks


}