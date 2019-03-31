package com.example.bitpchatportal;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class messagingActivity extends AppCompatActivity {

    FirebaseAuth auth1;

    String name="";
    String branch="";
    String semester = "",roll="";
    int number=0;

    EditText textFieldEditText;
    Button sendMsgButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference parentReference;
    DatabaseReference childReference;
    RecyclerView recyclerView;

    ArrayList<chat_list> chatlist = new ArrayList<>();
    chatAdapter mAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        auth1 = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference childReference = firebaseReference.child("registerUID");
        childReference.child(currentFirebaseUser.getUid()+"l").setValue(number);

        new Timer().schedule(new TimerTask(){
            public void run() {
                messagingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        auth1.signOut();
                        startActivity(new Intent(messagingActivity.this,MainActivity.class));
                    }
                });
            }
        }, 2000);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        textFieldEditText = findViewById(R.id.editTextField);
        sendMsgButton = findViewById(R.id.sendButton);

       name = getIntent().getExtras().getString("name");
       branch = getIntent().getExtras().getString("branch");
       semester = getIntent().getExtras().getString("semester");
       roll = getIntent().getExtras().getString("roll");

       recyclerView = findViewById(R.id.recyclerView);

        //getSupportActionBar().hide();
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference();

       parentReference = databaseReference.child(branch);
       childReference = parentReference.child(semester);

       sendMsgButton.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
           public void onClick(View v) {

               if(textFieldEditText.getText().toString().isEmpty()){
                   Toast.makeText(getApplicationContext(),"type some message",Toast.LENGTH_SHORT).show();
               }else {

                   LocalDateTime dt = LocalDateTime.now();
                   int dd = dt.getDayOfMonth();
                   int mm = dt.getMonthValue();
                   int yy = dt.getYear();
                   int h = dt.getHour();
                   int m = dt.getMinute();
                   int s = dt.getSecond();

                //   Log.i("RESULT IS HERE",currentTime.toString());
                   childReference.child(dd+":"+mm+":"+yy+" "+h+":"+m+":"+s+" "+roll+name).setValue(textFieldEditText.getText().toString());

                 //  Toast.makeText(getApplicationContext(),"successfully send",Toast.LENGTH_SHORT).show();
                   textFieldEditText.setText("");
               }

           }
       });

       mAdapter = new chatAdapter(chatlist);
       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
       recyclerView.setLayoutManager(mLayoutManager);
       recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.setAdapter(mAdapter);
       chatlist.clear();

    }

    @Override
    protected void onStart() {
        super.onStart();

        childReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatlist.clear();
                chat_list chat;
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    chat = new chat_list(dataSnapshot1.getKey().toString(),dataSnapshot1.getValue().toString());
                    chatlist.add(chat);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
