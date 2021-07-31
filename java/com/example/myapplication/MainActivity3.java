package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity3 extends AppCompatActivity {
    EditText e1,e2,e3,e4;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.contact);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contact:
                        return true;

                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.predict:
                        startActivity(new Intent(getApplicationContext(), Prediction.class));
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
        e1=findViewById(R.id.name1);
        e2=findViewById(R.id.email1);
        e3=findViewById(R.id.subject1);
        e4=findViewById(R.id.message1);
        bt=findViewById(R.id.button2);
    }
    public void onClick(View view)
    {
        String to="mkashi52744@gmail.com";
        String cc="hamzarauf514@gmail.com";
        String cc2="hassnainali89383@gmail.com";
        String name=e1.getText().toString();
        String subject=e3.getText().toString();
        String message=e4.getText().toString();
        String email=e2.getText().toString();

        if (TextUtils.isEmpty(name)){
            e1.setError("Enter Your Name");
            e1.requestFocus();
            return;
        }

        Boolean onError = false;


        if (!isValidEmail(email)) {
            onError = true;
            e2.setError("Invalid Email");
            return;
        }

        if (TextUtils.isEmpty(subject)){
            e3.setError("Enter Your Subject");
            e3.requestFocus();
            return;
        }

        onError = false;
        if (TextUtils.isEmpty(message)){
            e4.setError("Enter Your Message");
            e4.requestFocus();
            return;
        }
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
        mail.putExtra(Intent.EXTRA_CC, new String[]{cc,cc2});
        mail.putExtra(Intent.EXTRA_SUBJECT, "Name : "+name+"\n"+"Email : "+email+"\n"+"Subject : "+subject);
        mail.putExtra(Intent.EXTRA_TEXT, message);
        mail.setType("message/rfc822");
        startActivity(Intent.createChooser(mail, "Send email via:"));
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}