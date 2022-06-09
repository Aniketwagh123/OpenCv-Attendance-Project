package com.example.myapplicationnew.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplicationnew.LoginSignup.Login;
import com.example.myapplicationnew.R;

public class UserDashboard extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }
    public void user(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
}