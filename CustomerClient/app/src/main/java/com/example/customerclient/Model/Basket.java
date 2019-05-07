package com.example.customerclient.Model;

import java.util.ArrayList;

public class Basket {

    String restaurantId;
    ArrayList<BasketItem> items;

    public Basket(){
        items = new ArrayList<>();
    }

    public ArrayList<BasketItem> getItems() {
        return items;
    }

    public void addItem(BasketItem item){
        int index = findItemIndex(item.getMenuItemId());
        if(index == -1){
            items.add(item);
        } else {
            items.get(index).incrementQty();
        }
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int findItemIndex(String menuItemId){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).getMenuItemId() == menuItemId)
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "restaurantId='" + restaurantId + '\'' +
                ", items=" + items +
                '}';
    }
}
