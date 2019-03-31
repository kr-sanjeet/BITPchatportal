package com.example.bitpchatportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class signUpActivity extends AppCompatActivity {

    EditText emailText,passwordText;
    Button signUpBtn;
    ProgressBar progressBar; FirebaseAuth auth;
    EditText nameEditText,rollEditText;
    Spinner branchSpinner,semesterSpinner;
     FirebaseDatabase firebaseDatabase;
   // int number=1;
    String roll="";


    public void customAddValue(){
        ArrayList<String> list = new ArrayList<>();
        list.add("CSE");
        list.add("IT");
        list.add("ECE");
        list.add("EEE");
        list.add("CE");
        list.add("ME");
        list.add("PE");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        branchSpinner.setAdapter(arrayAdapter);

    }
    public void semesterAddValue(){
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1st");
        list1.add("2nd");
        list1.add("3rd");
        list1.add("4th");
        list1.add("5th");
        list1.add("6th");
        list1.add("7th");
        list1.add("8th");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list1);
        semesterSpinner.setAdapter(arrayAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailText = findViewById(R.id.recoveryEmailText);
        passwordText = findViewById(R.id.passwordEditText);
        signUpBtn = findViewById(R.id.signUpButton);
        nameEditText = findViewById(R.id.nameEmailText);
        rollEditText = findViewById(R.id.rollEmailText);
        progressBar = findViewById(R.id.progressBar);
        branchSpinner = findViewById(R.id.branchSpinner);
        semesterSpinner = findViewById(R.id.semesterSpinner);

        firebaseDatabase = FirebaseDatabase.getInstance();


        emailText.setText("");
        passwordText.setText("");

        progressBar.setVisibility(View.INVISIBLE);

        getSupportActionBar().hide();

        customAddValue();
        semesterAddValue();

        auth = FirebaseAuth.getInstance();


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"enter password !",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"enter email id !",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"please enter your name",Toast.LENGTH_SHORT).show();
                }
                else if(!rollEditText.getText().toString().isEmpty()){
                    roll=rollEditText.getText().toString();
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(signUpActivity.this, "Successfully created", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(signUpActivity.this, "Authentication Failed " + task.getException(), Toast.LENGTH_SHORT).show();


                                    } else {

                                        DatabaseReference firebaseReference  = firebaseDatabase.getReference();
                                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                        DatabaseReference childReference = firebaseReference.child("registerUID");

                                        childReference.child(currentFirebaseUser.getUid()+"n").setValue(nameEditText.getText().toString());
                                        childReference.child(currentFirebaseUser.getUid()+"b").setValue(branchSpinner.getSelectedItem().toString());
                                        childReference.child(currentFirebaseUser.getUid()+"s").setValue(semesterSpinner.getSelectedItem().toString());
                                        childReference.child(currentFirebaseUser.getUid()+"r").setValue(roll);
                                       // childReference.child(currentFirebaseUser.getUid()+"l").setValue(number);
                                        startActivity(new Intent(signUpActivity.this, MainActivity.class));

                                        finish();
                                    }

                                }
                            });

                    //SAVE UID OF NEW USER IN AUTHENTICATION FIELD
                }

            }
        });

    }
}
