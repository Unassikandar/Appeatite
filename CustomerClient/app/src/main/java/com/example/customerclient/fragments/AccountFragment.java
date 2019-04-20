package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.sql.DriverManager.println;
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


    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_account, container, false);
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
