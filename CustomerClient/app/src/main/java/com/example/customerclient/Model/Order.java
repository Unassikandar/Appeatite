package com.example.customerclient.Model;

import java.util.ArrayList;


public class Order {
    String tableId;
    ArrayList<OrderItem> list;
    float totalPrice;

    public Order(String tableId){
        this.tableId = tableId;
        list = new ArrayList<>();
        totalPrice = 0;
    }

    public void addItem(MenuItemData item){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getItem().getName() == item.getName()){
                int qty = list.get(i).getQuantity();
                list.get(i).setQuantity(qty+1);
                totalPrice += Double.parseDouble(item.getPrice());
                return;
            }
        }
        list.add(new OrderItem(item));
        totalPrice += Double.parseDouble(item.getPrice());
    }

    public void removeItem(MenuItemData item){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getItem().getName() == item.getName()){
                int qty = list.get(i).getQuantity();
                if(qty > 1){
                    list.get(i).setQuantity(qty-1);
                    totalPrice -= Double.parseDouble(item.getPrice());
                }else{
                    list.remove(i);
                    totalPrice -= Double.parseDouble(item.getPrice());
                }
            }
        }
    }

    public class OrderItem{
        MenuItemData item;
        int quantity;
        public OrderItem(MenuItemData item){
            this.item = item;
            this.quantity = 1;
        }

        public int getQuantity() {
            return quantity;
        }

        public MenuItemData getItem() {
            return item;
        }

        public void setItem(MenuItemData item) {
            this.item = item;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
