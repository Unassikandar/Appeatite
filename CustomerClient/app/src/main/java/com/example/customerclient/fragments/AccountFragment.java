package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customerclient.R;
import com.example.customerclient.activities.HomescreenActivity;
import com.example.customerclient.testing_stuff.Table;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AccountFragment extends Fragment {

    /******/
    private FirebaseFirestore db;
    private String key;
    /******/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        key = HomescreenActivity.getKey();
        db = FirebaseFirestore.getInstance();


        return view;


    }
}
