package com.example.bitpchatportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class chatActivity extends AppCompatActivity {

    FirebaseAuth auth1;

    FirebaseDatabase firebaseDatabase;
    String name="",branch="",semester="",roll="";
    FirebaseUser currentFirebaseUser;
    DatabaseReference databaseReference1;
    int number=0;
  ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        progressBar = findViewById(R.id.progressBar2);

        getData();

    }

        public void getData(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference1= databaseReference.child("registerUID");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.VISIBLE);

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){


                    if(userSnapshot.getKey().equals(currentFirebaseUser.getUid()+"n")){
                        name = userSnapshot.getValue().toString();
                    }
                    if(userSnapshot.getKey().equals(currentFirebaseUser.getUid()+"b")){
                        branch = userSnapshot.getValue().toString();
                    }
                    if(userSnapshot.getKey().equals(currentFirebaseUser.getUid()+"s")){
                        semester = userSnapshot.getValue().toString();
                    }
                    if(userSnapshot.getKey().equals(currentFirebaseUser.getUid()+"r")){
                        roll = userSnapshot.getValue().toString();
                    }
//                    if(userSnapshot.getKey().equals(currentFirebaseUser.getUid()+"l")){
//                        number = Integer.parseInt(userSnapshot.getValue().toString());
//                    }

                }

                Intent i = new Intent(chatActivity.this,messagingActivity.class);
                i.putExtra("name",name);
                i.putExtra("branch",branch);
                i.putExtra("semester",semester);
                i.putExtra("roll",roll);
              //i.putExtra("size",number);

                progressBar.setVisibility(View.GONE);
                startActivity(i);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
