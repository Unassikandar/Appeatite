package com.example.customerclient.Model;

public class MenuItemData {

    private String headingId;
    private String name;
    private String calories;
    private String expectedWaitTime;
    private String ingredients;
    private String discountPercent;
    private String price;


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

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getExpectedWaitTime() {
        return expectedWaitTime;
    }

    public void setExpectedWaitTime(String expectedWaitTime) {
        this.expectedWaitTime = expectedWaitTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItemData{" +
                "headingId='" + headingId + '\'' +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", expectedWaitTime=" + expectedWaitTime +
                ", ingredients='" + ingredients + '\'' +
                ", discountPercent=" + discountPercent +
                ", price=" + price +
                '}';
    }
}
