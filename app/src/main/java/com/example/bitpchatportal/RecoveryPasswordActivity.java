package com.example.bitpchatportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecoveryPasswordActivity extends AppCompatActivity {


    EditText recoveryEmailText;
    Button resetPasswordBtn,backMainPageBtn;
    ProgressBar recoveryProgressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);

        recoveryEmailText = findViewById(R.id.recoveryEmailText);
        resetPasswordBtn = findViewById(R.id.resetBtn);
        backMainPageBtn = findViewById(R.id.backBtn);
        recoveryProgressBar = findViewById(R.id.recoveryProgressBar);
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        recoveryProgressBar.setVisibility(View.INVISIBLE);

        backMainPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecoveryPasswordActivity.this,MainActivity.class));
            }
        });



        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoveryProgressBar.setVisibility(View.VISIBLE);
                final String email = recoveryEmailText.getText().toString().trim();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"We have sent you a link to reset password ",Toast.LENGTH_SHORT).show();
                                    recoveryProgressBar.setVisibility(View.GONE);
                                    recoveryEmailText.setText("");
                                    startActivity(new Intent(RecoveryPasswordActivity.this,MainActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(),"Failed to send reset email "+task.getException(),Toast.LENGTH_SHORT).show();
                                    recoveryProgressBar.setVisibility(View.GONE);
                                }

                            }
                        });


            }
        });
    }
}
