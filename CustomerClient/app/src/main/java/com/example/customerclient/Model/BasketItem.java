package com.example.customerclient.Model;

public class BasketItem {
    private String menuItemId;
    private int quantity;
    private String name;
    private String price;

    public BasketItem(String menuItemId, String name, String price){
        this.menuItemId = menuItemId;
        this.quantity = 1;
        this.name = name;
        this.price = price;
    }

    public void incrementQty(){
        this.quantity += 1;
    }

    public void decrementQty(){
        this.quantity -= 1;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "menuItemId='" + menuItemId + '\'' +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
