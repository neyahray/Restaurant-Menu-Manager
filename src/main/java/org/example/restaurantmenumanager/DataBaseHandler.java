package org.example.restaurantmenumanager;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseHandler extends CommonMethods {
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

    //CRUD METHODS
    //(C) CREATE TABLE
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
                    price REAL NOT NULL,
                    description TEXT
                );
                """;
//
//        String insertPastriesSQL = """
//                INSERT OR IGNORE INTO pastries (id, name, price, description) VALUES
//                (1, "Matcha waffle", 2.20, "Waffles with matcha"),
//                (2, "Dubai chocolate muffin", 1.80, "gold labubu"),
//                (3, "Red velvet", 2.90, "yummy"),
//                (4, "Blueberry doughnut", 0.90, "Dariia loves it");
//                """;
//
//        String insertDrinksSQL = """
//                INSERT OR IGNORE INTO drinks (id, name, price, description) VALUES
//                (1, "Matcha", 2.50, "better with coconut milk"),
//                (2, "Matcha-Mango", 2.80, "bonfire"),
//                (3, "Birch sap", 1.20, "very niche"),
//                (4, "Apple juice", 1.50, "yummy"),
//                (5, "Orange juice", 1.50, "yummy"),
//                (6, "Apricote compote", 1.80, "grape's"),
//                (7, "Pineapple Energy drink", 1.90, "free for Dariia");
//                """;

        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute(pastriesSQL);
            stmt.execute(drinksSQL);
//            stmt.execute(insertPastriesSQL);
//            stmt.execute(insertDrinksSQL);
            System.out.println("Tables created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //(C) CREATE AND ADD ITEM
    public static void addItem(Item item, String tableName) {
        String sql = "INSERT INTO " + tableName + " (name, price, description) VALUES ( ?, ?, ?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //(R) GETTING DATA
    public static ArrayList<Item> getAllItems(String tableName) {
        ArrayList<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");

                if (tableName.equals("pastries")) {
                    items.add(new Pastry(id, name, price, description));
                } else if (tableName.equals("drinks")) {
                    items.add(new Drink(id, name, price, description));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    //UPDATE (U-Update)
    public static void updateItem (int id, String name, Double price, String description, String tableName) {
        String sql = "UPDATE " + tableName + " SET name = ?, price = ?, description = ? WHERE id = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setString(3, description);
            stmt.setInt(4, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //DELETE (D-Delete)
    public static void deleteItem (int id, String tableName) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}