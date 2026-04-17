package org.example.restaurantmenumanager;

public class Drink extends Item {
    public Drink(int id, String name, double price, String description) {
        super(id, name, price, description);
    }

    public Drink(String name, double price, String description) {
        super(name, price, description);
    }

    @Override
    public String getTableName() {
        return "drinks";
    }
}