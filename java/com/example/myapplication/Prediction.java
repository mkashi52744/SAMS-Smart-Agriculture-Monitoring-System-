package com.example.myapplication;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Prediction extends AppCompatActivity {
    TextView t1,t2,t3,t4,view_2,view_1;
    EditText e1;
    Button add;
    Switch sw;
    Interpreter interpreter;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference ref1=firebaseDatabase.getReference("Humidity");
    DatabaseReference ref2=firebaseDatabase.getReference("Temperature");
    DatabaseReference ref3=firebaseDatabase.getReference("Moisture");
    DatabaseReference ref4=firebaseDatabase.getReference("Days");
    DatabaseReference ref5=firebaseDatabase.getReference("Status");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        try {
            interpreter = new Interpreter(loadModelFile(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sw=findViewById(R.id.switch1);
        t1 = (TextView) findViewById(R.id.text5);
        t2 = (TextView) findViewById(R.id.text6);
        t3 = (TextView) findViewById(R.id.text7);
        t4 = (TextView) findViewById(R.id.text8);
        view_1 = (TextView) findViewById(R.id.textView10);
        ref4.setValue("00");
        e1=findViewById(R.id.days);
        add=findViewById(R.id.add);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.predict);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.predict:
                        return true;

                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
        {
            ref5.setValue("1");
        }
        else
        {
            ref5.setValue("0");
        }
            }
        });


        ref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status=snapshot.getValue(String.class);
                int a= Integer.parseInt(status);
                if (a==1)
                {
                    sw.setChecked(true);
                }
                else
                {
                    sw.setChecked(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String humidity=snapshot.getValue(String.class);
                t1.setText(humidity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp=snapshot.getValue(String.class);
                t2.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mositure=snapshot.getValue(String.class);
                t3.setText(mositure);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String days=snapshot.getValue(String.class);
                t4.setText(days);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void add(View view)
    {
        String days=e1.getText().toString();
        if (TextUtils.isEmpty(days)){
            e1.setError("Enter Days");
            e1.requestFocus();
            return;
        }
        else {
            ref4.setValue(days);
        }
    }
    private MappedByteBuffer loadModelFile() throws IOException
    {
        AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("water2.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOfferset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOfferset, length);
    }
    public String doInference(String val, String val2, String val3, String val4){
        float [] input = new float[4];
        input[0] = Float.parseFloat(val);
        input[1] = Float.parseFloat(val2);
        input[2] = Float.parseFloat(val3);
        input[3] = Float.parseFloat(val4);

        float[][] output = new float[1][2];
        String status;

        interpreter.run(input, output);


        if(output[0][0]>output[0][1]){
            status = "No Need Of Water.";
        }
        else    status = "It's Time to Irrigate.";

        return status;
    }
    public void predict(View view)
    {
        String num1 = t3.getText().toString();
        String num2 = t2.getText().toString();
        String num3 = t1.getText().toString();
        String num4 = t4.getText().toString();

        String s = doInference(num1, num2, num3, num4);
        view_1.setText(s);


    }
}