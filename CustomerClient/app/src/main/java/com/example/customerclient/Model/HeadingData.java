package com.example.customerclient.Model;

class HeadingData {
    private String restaurantId;
    private String headingId;
    private String name;

    //GETTERS AND SETTERS
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getHeadingId() {
        return headingId;
    }

    public void setHeadingId(String headingId) {
        this.headingId = headingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeadingData{" +
                "restaurantId='" + restaurantId + '\'' +
                ", headingId='" + headingId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
