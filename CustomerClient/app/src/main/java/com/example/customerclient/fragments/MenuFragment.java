package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.customerclient.ServerComms.ServerApi;
import com.example.customerclient.R;
import com.example.customerclient.activities.HomescreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    /******/
    private FirebaseFirestore db;
    private String restId, tableId;
    private ListView listView;
    private TextView textView;
    private ArrayList<String> menuHeaders;
    private ServerApi serverApi;
    /******/

    private TextView textViewResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        textView = view.findViewById(R.id.restaurant_name);
        listView = view.findViewById(R.id.menu_frag_list);

        tableId = HomescreenActivity.getTableId();
        restId = HomescreenActivity.getRestId();

        textView.setText(restId);


//        /* PUT ITEMS IN THE LISTVIEW */
//        menuHeaders = new ArrayList<>();
        /*puut items in menuheaders */
//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                menuHeaders
//        );
//        listView.setAdapter(listViewAdapter);

        return view;
    }



}


