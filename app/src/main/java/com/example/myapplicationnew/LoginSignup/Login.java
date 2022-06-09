package com.example.myapplicationnew.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplicationnew.R;
import com.example.myapplicationnew.User.UserDashboard;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void back_from_login(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }
    public void CreateAccountBtn(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
    }

}