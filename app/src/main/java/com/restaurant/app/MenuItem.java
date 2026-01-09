package com.restaurant.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "menu_items")
public class MenuItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double price;
    private String imagePath;
    private int stockQuantity;
    private boolean needsRestock;
    private String restockNote;

    public MenuItem(String name, double price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.stockQuantity = 50; // Default stock
        this.needsRestock = false;
        this.restockNote = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isNeedsRestock() {
        return needsRestock;
    }

    public void setNeedsRestock(boolean needsRestock) {
        this.needsRestock = needsRestock;
    }

    public String getRestockNote() {
        return restockNote;
    }

    public void setRestockNote(String restockNote) {
        this.restockNote = restockNote;
    }
}