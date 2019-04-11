package com.example.customerclient.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.customerclient.R;

public class SettingsFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        Button buttonApply = view.findViewById(R.id.button3);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });*/

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void checkButton(View v)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
    }


}
