package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity2 extends AppCompatActivity {

    ProgressBar myprogressBar,myprogressBar2,myprogressBar3,myprogressBar4;
    TextView t1,t2,t3,t4;
    Button btn;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference("Humidity");
    DatabaseReference databaseReference1=firebaseDatabase.getReference("Temperature");
    DatabaseReference databaseReference2=firebaseDatabase.getReference("Moisture");
    DatabaseReference databaseReference3=firebaseDatabase.getReference("WaterLevel");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myprogressBar = (ProgressBar) findViewById(R.id.progressBar);
        t1 = (TextView) findViewById(R.id.progress_circle_text);
        myprogressBar2 = (ProgressBar) findViewById(R.id.progressBar1);
        t2 = (TextView) findViewById(R.id.progress_circle_text1);
        myprogressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        t3 = (TextView) findViewById(R.id.progress_circle_text3);
        myprogressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        t4 = (TextView) findViewById(R.id.progress_circle_text4);
        btn=findViewById(R.id.button3);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String humidity=snapshot.getValue(String.class);
                t1.setText(humidity);
                myprogressBar.setMax(100);
                myprogressBar.setProgress(Integer.parseInt(humidity));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp=snapshot.getValue(String.class);
                t2.setText(temp);
                myprogressBar2.setMin(0);
                myprogressBar2.setMax(100);
                myprogressBar2.setProgress(Integer.parseInt(temp));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String moisture=snapshot.getValue(String.class);
                t3.setText(moisture);
                myprogressBar3.setMin(0);
                myprogressBar3.setMax(10000);
                int n= Integer.parseInt(t3.getText().toString());
                int o=n*100;
                myprogressBar3.setProgress(o);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String level=snapshot.getValue(String.class);
                t4.setText(level);
                myprogressBar4.setMin(0);
                myprogressBar4.setMax(10000);
                int l= Integer.parseInt(t4.getText().toString());
                int m=l*2500;
                myprogressBar4.setProgress(m);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                        overridePendingTransition(0, 0);
                        finish();

                    case R.id.predict:
                        startActivity(new Intent(getApplicationContext(), Prediction.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
    public void clear(View view)
    {
        t1.setText("0");
        t2.setText("0");
        t3.setText("0");
        t4.setText("0");
        myprogressBar.setProgress(0);
        myprogressBar2.setProgress(0);
        myprogressBar3.setProgress(0);
        myprogressBar4.setProgress(0);

    }
    public void start(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
        overridePendingTransition(0, 0);
        finish();
    }

    }