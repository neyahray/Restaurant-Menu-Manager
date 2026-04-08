package org.example.restaurantmenumanager;

public class Pastry {
    private int id;
    private String name;
    private double price;
    private String description;

    public Pastry(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

}
