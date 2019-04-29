package com.example.customerclient.Model;

import java.util.ArrayList;

public class MenuItems {

    private String status;
    private ArrayList<MenuItemData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MenuItemData> getData() {
        return data;
    }

    public void setData(ArrayList<MenuItemData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MenuItems{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
