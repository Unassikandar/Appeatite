package com.example.customerclient.Model;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

    //ATTRIBUTES
    private String status;
    private String restaurantId;

    //GETTERS AND SETTERS
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "status=" + status +
                ", restaurantId='" + restaurantId + '\'' +
                '}';
    }
}
