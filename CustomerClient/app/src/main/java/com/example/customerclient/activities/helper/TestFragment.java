package com.example.customerclient.activities.helper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customerclient.R;

public class TestFragment extends Fragment {

    public static TestFragment init (String name){
        TestFragment newFragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        newFragment.setArguments(args);
        return newFragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_list, container, false);
        TextView tv = layoutView.findViewById(R.id.fragment_text);
        tv.setText("OnCreate set Text");
        Log.d("creating view", "111");
        return layoutView;
    }

}
