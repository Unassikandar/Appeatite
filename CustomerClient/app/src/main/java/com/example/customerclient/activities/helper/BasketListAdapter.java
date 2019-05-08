package com.example.customerclient.activities.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;

import java.util.ArrayList;

public class BasketListAdapter extends ArrayAdapter<BasketItem> {

    ArrayList<BasketItem> items;
    Context mContext;

    public BasketListAdapter(Context context, ArrayList<BasketItem> items){
        super(context, 0, items);
        this.mContext = context;
        this.items = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        Button removebtn = convertView.findViewById(R.id.basket_row_removeBtn);
        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket basket = CloudFunctions.getInstance().getBasket();
                basket.removeItem(bItem.getMenuItemId());
                CloudFunctions.getInstance().setBasket(basket);
//                Log.i("removeBasket", "X");
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
