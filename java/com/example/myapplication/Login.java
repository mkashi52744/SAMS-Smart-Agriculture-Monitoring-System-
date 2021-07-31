package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText username1,password1;
    CheckBox chk;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        chk=findViewById(R.id.checkBox);
        username1=findViewById(R.id.username);
        password1=findViewById(R.id.password);
        login=findViewById(R.id.loginbtn);
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b)
                {
                    password1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else
                {
                    password1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
    }
    public void login(View view) {
        String email = username1.getText().toString();
        String pass = password1.getText().toString();
        String email1 = "admin";
        String pass1 = "admin";
        if (email.equals(email1) && pass.equals(pass1)) {
            Intent intent=new Intent(this,MainActivity2.class);
            startActivity(intent);
        } else if (email != email1 && pass != pass1) {
            Toast toast = Toast.makeText(this, "Invalid Email or Password!!!!!", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}