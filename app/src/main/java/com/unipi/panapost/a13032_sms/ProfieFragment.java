package com.unipi.panapost.a13032_sms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ProfieFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef, myRef1;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private User user;

    private TextInputEditText editName, editHome, email;
    private ConstraintLayout parent;
    private MySnackBar mySnackBar;
    private Button editProfile;


    public ProfieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profie,container,false);

        editProfile = view.findViewById(R.id.editProfile);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        currentUser = mAuth.getCurrentUser();

        editName = view.findViewById(R.id.editName);
        editHome = view.findViewById(R.id.editHome);
        email = view.findViewById(R.id.email);
        email.setKeyListener(null);
        parent = getActivity().findViewById(R.id.parent);
        mySnackBar = new MySnackBar();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                editName.setText(user.getUsername());
                editHome.setText(user.getAddress());
                email.setText(user.getEmail());
                //Toast.makeText(getContext(), user.getAddress(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editProfile.setOnClickListener(v -> change());
        return view;
    }

    public void change(){
        User user1 = new User(editName.getText().toString(),user.getEmail(),editHome.getText().toString(),user.getApp_theme(),user.getHistory());
        myRef.setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mySnackBar.show(getString(R.string.successful), getActivity().getApplicationContext(), editProfile, false);
            }
        });
    }
}

