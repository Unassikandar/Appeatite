package com.example.customerclient.Model;

import java.util.ArrayList;

public class Headings {
    private String status;
    private ArrayList<HeadingData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<HeadingData> getData() {
        return data;
    }

    public void setData(ArrayList<HeadingData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Headings{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
