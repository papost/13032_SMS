package com.unipi.panapost.a13032_sms;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SmsFragment extends Fragment  {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button button;
    private final String phone = "13032";
    private User user;
    private History history;
    private DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    private String date;

    private TextInputEditText myName, myHome;
    private ConstraintLayout parent;
    private MySnackBar mySnackBar;

    public SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sms,container,false);

        button = v.findViewById(R.id.button8);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        currentUser = mAuth.getCurrentUser();

        myName = v.findViewById(R.id.myName);
        myName.setKeyListener(null);
        myHome = v.findViewById(R.id.editPhone);
        myHome.setKeyListener(null);
        parent = getActivity().findViewById(R.id.parent);
        mySnackBar = new MySnackBar();

        currentUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        button.setOnClickListener(v1 ->{
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.SEND_SMS},5435);
                return;
            }else {
                date = dateFormat.format(Calendar.getInstance().getTime());
                user= new User(user.getUsername(),user.getEmail(),user.getAddress(),user.getApp_theme(),date);
                //myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
                myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });
                StringBuilder builder = new StringBuilder();
                builder.append(user.getUsername()).append(" ").append(user.getAddress());
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, builder.toString(), null, null);
                //Toast.makeText(getContext(),"vvvv",Toast.LENGTH_LONG).show();}

            } } );

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                myName.setText(user.getUsername());
                myHome.setText(user.getAddress());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }




}