package com.restaurant.app.api;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    private String message;

    // Getter
    public String getMessage() {
        return message;
    }
}