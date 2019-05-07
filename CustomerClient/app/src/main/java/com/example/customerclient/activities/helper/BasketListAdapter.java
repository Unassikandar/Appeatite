package com.example.customerclient.activities.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.customerclient.Model.Basket;
import com.example.customerclient.Model.BasketItem;
import com.example.customerclient.Model.MenuItemData;
import com.example.customerclient.R;

public class BasketListAdapter extends ArrayAdapter<BasketItem> {

    Basket basket;
    Context mContext;

    public BasketListAdapter(Context context, Basket basket){
        super(context, 0, basket.getItems());
        this.basket = basket;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BasketItem bItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_basket, parent, false);
        }
        //set quantity
        TextView tvQty = convertView.findViewById(R.id.basket_row_quantity);
        String qty = String.valueOf(bItem.getQuantity());
        tvQty.setText(qty);
        TextView tvName = convertView.findViewById(R.id.basket_row_itemName);
        tvName.setText(bItem.getName());
        TextView tvPrice = convertView.findViewById(R.id.basket_row_price);
        Double price = Double.parseDouble(bItem.getPrice()) * bItem.getQuantity();
        tvPrice.setText(price.toString() + " TL");

        return convertView;
    }
}
