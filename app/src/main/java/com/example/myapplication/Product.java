package com.example.myapplication;

public class Product {
    private String name;
    private String description;
    private int price;
    private String emoji;
    private int quantity;

    public Product(String name, String description, int price, String emoji) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.emoji = emoji;
        this.quantity = 1;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public String getEmoji() { return emoji; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}