package com.restaurant.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tables")
public class Table {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tableName;
    private int capacity;
    private String status; // Available, Occupied, Reserved
    private String currentOrder;
    private int itemCount;

    public Table(String tableName, int capacity, String status) {
        this.tableName = tableName;
        this.capacity = capacity;
        this.status = status;
        this.currentOrder = "";
        this.itemCount = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(String currentOrder) {
        this.currentOrder = currentOrder;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
