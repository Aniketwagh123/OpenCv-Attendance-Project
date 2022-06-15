package com.example.myapplicationnew.LoginSignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationnew.Databases.DbsDoc;
import com.example.myapplicationnew.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import com.example.myapplicationnew.Databases.DbsDoc;

import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

public class SignUp extends AppCompatActivity {
    TextInputLayout name,branch,div,roll_no,email,password;
    public static String name_val,branch_val,div_val,roll_no_val,email_val,password_val;

    SharedPreferences shrd;
    SharedPreferences.Editor editor;
    public boolean is_clicked_Create_acc=false;

    DbsDoc dbsDoc ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbsDoc = new DbsDoc();
        //hooks
        name = findViewById(R.id.std_name);
        branch = findViewById(R.id.std_branch);
        div = findViewById(R.id.std_div);
        roll_no = findViewById(R.id.std_roll_no);
        email = findViewById(R.id.std_email);
        password = findViewById(R.id.std_password);

        shrd = getSharedPreferences("create",MODE_PRIVATE);
        editor=shrd.edit();
    }



    public void back_from_login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }
    public void createAccount_click(View view) throws InterruptedException {
        name_val = Objects.requireNonNull(Objects.requireNonNull(name.getEditText()).getText()).toString();
        branch_val = Objects.requireNonNull(Objects.requireNonNull(branch.getEditText()).getText()).toString();
        div_val = Objects.requireNonNull(Objects.requireNonNull(div.getEditText()).getText()).toString();
        roll_no_val = Objects.requireNonNull(Objects.requireNonNull(roll_no.getEditText()).getText()).toString();
        email_val = Objects.requireNonNull(Objects.requireNonNull(email.getEditText()).getText()).toString();
        password_val = Objects.requireNonNull(Objects.requireNonNull(password.getEditText()).getText()).toString();
        startActivity(new Intent(getApplicationContext(), Login.class));
        Log.v("test2",name_val+" "+roll_no_val+" "+email_val+" ");
    }

}