package com.example.listneeds;

public class Item {
    private int id;
    private String date;
    private String name;
    private int quantity;
    private String description;
    private int appPrice;


    public Item(){}

    public Item(int id, String date, String name, int quantity, String description, int appPrice) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.appPrice = appPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(int appPrice) {
        this.appPrice = appPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", appPrice=" + appPrice +
                '}';
    }
}
