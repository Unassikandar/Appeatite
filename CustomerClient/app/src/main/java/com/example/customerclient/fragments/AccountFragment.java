package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.sql.DriverManager.println;
=======
import android.widget.TextView;

import com.example.customerclient.R;
import com.example.customerclient.activities.HomescreenActivity;
import com.example.customerclient.testing_stuff.Table;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
>>>>>>> 12af417a8c4567107f41ddb33a1fb67aaf663445

public class AccountFragment extends Fragment {

    /******/
    private FirebaseFirestore db;
    private String key;
    /******/

    @Nullable


    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
<<<<<<< HEAD
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_account, container, false);
=======

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        key = HomescreenActivity.getKey();
        db = FirebaseFirestore.getInstance();


        return view;


>>>>>>> 12af417a8c4567107f41ddb33a1fb67aaf663445
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView tv = (TextView) getView().findViewById(R.id.textview0);
        String temp = currentUser.getEmail();
        tv.append(temp);
    }
}
