package com.example.customerclient.activities.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customerclient.R;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChildFragment extends Fragment {

    private String name, calories, expectedWaitTime, ingredients, discountPercent, price;

    RelativeLayout relativeLayout;

    public static ChildFragment init (String name,
                                      String calories,
                                      String expectedWaitTime,
                                      String ingredients,
                                      String discountPercent,
                                      String price){
        ChildFragment childFragment = new ChildFragment();
        Bundle args = new Bundle();
//        Log.d("FragVal const", String.valueOf(fragVal));
//        args.putInt("fragVal", fragVal);
        String[] data = {name, calories, expectedWaitTime, ingredients, discountPercent, price};
        args.putStringArray("data", data);
        childFragment.setArguments(args);
        return childFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] data = getArguments().getStringArray("data");
        name = data[0];
        calories = data[1];
        expectedWaitTime = data[2];
        ingredients = data[3];
        discountPercent = data[4];
        price = data[5];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ListView lv = rootView.findViewById(R.id.menu_item_listview);
        lv.setVisibility(View.INVISIBLE);
        relativeLayout = rootView.findViewById(R.id.child_frag_layout);
        ImageView picView = relativeLayout.findViewById(R.id.child_item_pic);
        picView.setVisibility(View.VISIBLE);
        TextView nameTV = relativeLayout.findViewById(R.id.child_item_name);
        nameTV.setVisibility(View.VISIBLE);
        nameTV.setText(name);
        TextView ingredientsTV = relativeLayout.findViewById(R.id.child_item_ingredients);
        ingredientsTV.setVisibility(View.VISIBLE);
        ingredientsTV.setText(ingredients);
        TextView caloriesTV = relativeLayout.findViewById(R.id.child_item_calories);
        caloriesTV.setVisibility(View.VISIBLE);
        caloriesTV.setText(calories);
        TextView ewtTV = relativeLayout.findViewById(R.id.child_item_expectedWaitTime);
        ewtTV.setVisibility(View.VISIBLE);
        ewtTV.setText(expectedWaitTime);
        ImageView button = relativeLayout.findViewById(R.id.child_button);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Fragment> fragments = getChildFragmentManager().getFragments();
                getChildFragmentManager().beginTransaction().remove(ChildFragment.this).commit();
//                getActivity().getSupportFragmentManager().beginTransaction().remove(ChildFragment.this).commit();
            }
        });

        return rootView;
    }




}
