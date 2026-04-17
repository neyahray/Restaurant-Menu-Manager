package org.example.restaurantmenumanager;

public abstract class Item {
    private int id;
    private String name;
    private double price;
    private String description;

    public Item(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Item(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    //getters
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

    //polymorphism
    public abstract String getTableName();

    //setters
    public void setName(String name) { this.name = name; }

    public void setPrice(Double price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }
}
