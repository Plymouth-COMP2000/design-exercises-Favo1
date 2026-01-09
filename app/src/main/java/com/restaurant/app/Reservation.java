package com.restaurant.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservations")
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String customerName;
    private String date;
    private String time;
    private int numberOfGuests;
    private String status; // e.g., Confirmed, Pending, Cancelled
    private String createdBy; // Username of who created it
    private String notes; // Special requests or notes
    private String reservationType; // Birthday, Dinner, Custom

    public Reservation(String customerName, String date, String time, int numberOfGuests, String status) {
        this.customerName = customerName;
        this.date = date;
        this.time = time;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.notes = "";
        this.reservationType = "Dinner"; // Default type
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReservationType() {
        return reservationType;
    }

    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
    }
}