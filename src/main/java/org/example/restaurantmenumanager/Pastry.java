package org.example.restaurantmenumanager;

public class Pastry extends Item {
    public Pastry(int id, String name, double price, String description) {
        super(id, name, price, description);
    }

    public Pastry(String name, double price, String description) {
        super(name, price, description);
    }

    @Override
    public String getTableName() {
        return "pastries";
    }
}
