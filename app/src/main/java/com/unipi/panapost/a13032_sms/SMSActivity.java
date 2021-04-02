package com.unipi.panapost.a13032_sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.RowId;

public class SMSActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String selectedTheme;
    private int selectedBottom;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        selectedBottom = getIntent().getIntExtra("selectedBottom",R.id.smsFragment);
        selectedTheme = getIntent().getStringExtra("selectedTheme");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(selectedBottom);

        if(selectedBottom == R.id.smsFragment){
            getFragment(new SmsFragment());
        } else if (selectedBottom == R.id.settingsFragment){
            Bundle data = new Bundle();
            data.putString("selectedTheme", selectedTheme);
            Fragment fragment = new SettingsFragment();
            fragment.setArguments(data);
            getFragment(fragment);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.smsFragment){
                getFragment(new SmsFragment());
            } else if (item.getItemId() == R.id.profileFragment){
                getFragment(new ProfieFragment());
            }else if (item.getItemId() == R.id.settingsFragment){
                Bundle data = new Bundle();
                data.putString("selectedTheme", selectedTheme);
                Fragment fragment = new SettingsFragment();
                fragment.setArguments(data);
                getFragment(fragment);
            }
            return true;

        });


    }

    public void getFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alert_dialog = LayoutInflater.from(this).inflate(R.layout.quit_dialog,null);
        builder.setView(alert_dialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button signOut = alert_dialog.findViewById(R.id.quitRight);
        Button exit = alert_dialog.findViewById(R.id.quitCenter);

        //If the user presses the exit button the application closes
        exit.setOnClickListener(view -> {
            dialog.dismiss();
            finishAffinity();
        });
        //If the user presses the sign out button the user signs out and the Login Activity is now open
        signOut.setOnClickListener(view -> {
            dialog.dismiss();
            finish();
            mAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}