package com.project.hkwt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;


public class SettingsFragment extends Fragment {

    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    public SettingsFragment() {
        // Required empty public constructor
    }


    RadioGroup LangSelections;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckBox checkBox;
        checkBox = getView().findViewById(R.id.checkBox);
        sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night",false);

        if (nightMode){
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });

        TextView textView;
        textView = getView().findViewById(R.id.language_sett);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_settingsFragment_to_settingsActivity);
            }
        });
        textView = getView().findViewById(R.id.logout);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_settingsFragment_to_logoutActivity);
            }
        });


    }
}