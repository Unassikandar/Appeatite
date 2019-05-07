package com.example.customerclient.Model;

public class BasketItem {
    private String menuItemId;
    private int quantity;

    public BasketItem(String menuItemId){
        this.menuItemId = menuItemId;
        this.quantity = 1;
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

    @Override
    public String toString() {
        return "BasketItem{" +
                "menuItemId='" + menuItemId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
