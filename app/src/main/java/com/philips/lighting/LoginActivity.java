package com.philips.lighting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.philips.lighting.quickstart.R;

//This code will create the login that we will need for the app
//TODO need to autheticate with server
//TODO need to connect to create new user
//TODO need to connect to main screen
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText eName;
    private EditText ePassword;
    private Button eLogin;
    boolean isValid = false;
    private Button signUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        goMainActivity();

        eName = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignUp);

        //Clicks the new user button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCreateNewUser();
                Toast.makeText(LoginActivity.this,"Click on Signup Button",Toast.LENGTH_SHORT).show();
            }
        });

        //clicks to attempt login
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = eName.getText().toString();
                String inputPassword= ePassword.getText().toString();

                Toast.makeText(LoginActivity.this,"Click on Login Button",Toast.LENGTH_SHORT).show();

                if (inputName.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill out both Login and Password", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(LoginActivity.this, inputPassword, Toast.LENGTH_SHORT).show();
                    loginUser(inputName,inputPassword);

                }
            }
        });

    }

    private void goCreateNewUser() {
        //this will go to create user screen
    }

    private void loginUser(String inputName, String inputPassword) {
        goMainActivity();
    }


    private void goMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
