package com.example.bitpchatportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton,mSignButton,forgotBtn;
    String branch="",semester="",name="";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField = findViewById(R.id.recoveryEmailText);
        mPasswordField = findViewById(R.id.editText2);
        mLoginButton = findViewById(R.id.signInButton);
        mSignButton = findViewById(R.id.signUpButton);
        forgotBtn = findViewById(R.id.forgotButton);

        mEmailField.setText("");
        mPasswordField.setText("");

        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();


        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RecoveryPasswordActivity.class));
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                   startActivity(new Intent(MainActivity.this,chatActivity.class));
                }

            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });

        mSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,signUpActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(MainActivity.this,"field are empty",Toast.LENGTH_SHORT).show();

        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"sign in problem",Toast.LENGTH_SHORT).show();
                    }else{

                        startActivity(new Intent(MainActivity.this,chatActivity.class));
                    }
                }
            });


        }

    }
}
