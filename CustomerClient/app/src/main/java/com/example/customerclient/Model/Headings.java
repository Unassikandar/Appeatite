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

    public String getHeadingId(){
        if(data.size()>0)
            return this.data.get(0).getHeadingId();
        else
            return null;
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        for(HeadingData item : this.data){
            names.add(item.getName());
        }
        return names;
    }

    public int getHeadingsCount(){
        return this.data.size();
    }

    public ArrayList<String> getIds(){
        ArrayList<String> ids = new ArrayList<>();
        for(HeadingData item : this.data){
            ids.add(item.getHeadingId());
        }
        return ids;
    }

    @Override
    public String toString() {
        return "Headings{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
