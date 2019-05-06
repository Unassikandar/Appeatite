package com.example.customerclient.activities.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.customerclient.Model.MenuItemData;
import com.example.customerclient.Model.MenuItems;
import com.example.customerclient.R;

import java.util.ArrayList;

public class MenuListAdapter extends ArrayAdapter<MenuItemData> {

    public MenuListAdapter(Context context, ArrayList<MenuItemData> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MenuItemData menuItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }
        // Lookup view for data population
        TextView menuItemName = convertView.findViewById(R.id.test_item_name);
        TextView menuItemIngredients = convertView.findViewById(R.id.test_item_ingredients);
        TextView menuItemPrice = convertView.findViewById(R.id.test_item_price);
        // Populate the data into the template view using the data object
        menuItemName.setText(menuItem.getName());
        menuItemIngredients.setText(menuItem.getIngredients());
        String price = menuItem.getPrice() + " TL";
        menuItemPrice.setText(price);
        // Handle button listener
        Button addItemBtn = convertView.findViewById(R.id.test_addbtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Item to basket
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }


}
