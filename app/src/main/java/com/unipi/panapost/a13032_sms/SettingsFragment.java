package com.unipi.panapost.a13032_sms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchTheme;
    private String selectedTheme;
    private MySnackBar mySnackBar;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings,container,false);

        selectedTheme = getArguments().getString("selectedTheme");

        switchTheme = v.findViewById(R.id.switchTheme);
        if(selectedTheme.equals("Dark")){
            switchTheme.setChecked(true);
        } else  {
            switchTheme.setChecked(false);
        }

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        currentUser = mAuth.getCurrentUser();

        switchTheme.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                selectedTheme = "Dark";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                selectedTheme = "Light";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            myRef.child("Users").child(currentUser.getUid()).child("app_theme")
                    .setValue(selectedTheme);
            Intent intent = new Intent(getContext(), SMSActivity.class);
            intent.putExtra("selectedBottom",R.id.settingsFragment);
            intent.putExtra("selectedTheme",selectedTheme);
            getActivity().finish();
            startActivity(intent);
        }));
        return v;
    }
}