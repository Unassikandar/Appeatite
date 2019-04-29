package com.example.customerclient.activities.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.customerclient.Model.HeadingData;
import com.example.customerclient.Model.Headings;
import com.example.customerclient.Model.MenuItemData;
import com.example.customerclient.Model.MenuItems;
import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;

import java.util.ArrayList;

public class TestFragment extends Fragment {

    ArrayList<MenuItems> menuItems;
    int fragVal;

    public static TestFragment init (int fragVal){
        TestFragment newFragment = new TestFragment();
        Bundle args = new Bundle();
        Log.d("FragVal const", String.valueOf(fragVal));
        args.putInt("fragVal", fragVal);
        newFragment.setArguments(args);

        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : -1;
        Log.d("FragVal", String.valueOf(fragVal));
        menuItems = new ArrayList<>();
        menuItems = CloudFunctions.getInstance().getTempListMenu();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = layoutView.findViewById(R.id.menu_item_listview);
        ListAdapter listAdapter = new MenuListAdapter(getActivity(), menuItems.get(fragVal).getData());
        listView.setAdapter(listAdapter);

        TextView tv = layoutView.findViewById(R.id.test_text);
        tv.setText("this is frag: " + fragVal);

        return layoutView;
    }





}
