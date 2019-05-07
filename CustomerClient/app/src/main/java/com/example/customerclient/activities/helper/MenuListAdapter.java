package com.example.customerclient.activities.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.customerclient.Model.Basket;
import com.example.customerclient.Model.BasketItem;
import com.example.customerclient.Model.MenuItemData;
import com.example.customerclient.Model.MenuItems;
import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;

public class MenuListAdapter extends ArrayAdapter<MenuItemData> {

    Context context;
    FragmentManager childFm;

    public MenuListAdapter(Context context, ArrayList<MenuItemData> items, FragmentManager fm) {
        super(context, 0, items);
        this.context = context;
        this.childFm = fm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final MenuItemData menuItem = getItem(position);
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
        // Handle button listeners
        Button addItemBtn = convertView.findViewById(R.id.test_addbtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket basket = CloudFunctions.getInstance().getBasket();
                basket.addItem(new BasketItem(menuItem.getMenuItemId()));
                CloudFunctions.getInstance().setBasket(basket);
                Log.i("Add item", basket.toString());
            }
        });
        Button detailsButton = convertView.findViewById(R.id.test_detailsbtn);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildFragment frag = ChildFragment.init(menuItem.getName(), menuItem.getCalories(), menuItem.getExpectedWaitTime(), menuItem.getIngredients(), menuItem.getDiscountPercent(), menuItem.getPrice());
                FragmentTransaction t = childFm.beginTransaction();
                t.add(R.id.child_frag_layout, frag).commit();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }


}
