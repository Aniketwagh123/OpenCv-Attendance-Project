package com.example.myapplicationnew.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplicationnew.R;
import com.example.myapplicationnew.User.UserDashboard;

import org.bson.BsonObjectId;
import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import com.example.myapplicationnew.LoginSignup.SignUp;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {

    String Appid = "application-0-ovhjc";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    TextInputLayout checkName,checkPassword;

    SharedPreferences isLodgedIn,checkNameSrd,checkPasswordSrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("test3", "function called");
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());

//        Credentials credentials = Credentials.anonymous();
        Credentials credentials = Credentials.emailPassword("waniket48@gmail.com","Aniket@9277");
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()){
                Log.v("User","Logged in successfully");
                if(true) {
                    User user = app.currentUser();
                    assert user != null;
                    mongoClient = user.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("test-database");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("collections");

                    if(SignUp.name_val!=null) {
                        Document newDoc = new Document("name", SignUp.name_val);
                        newDoc.put("branch", SignUp.branch_val);
                        newDoc.put("div", SignUp.div_val);
                        newDoc.put("roll_no", SignUp.roll_no_val);
                        newDoc.put("email", SignUp.email_val);
                        newDoc.put("password", SignUp.password_val);
                        List<String> lists=new ArrayList<String>();
                        lists.add("0,0");
                        lists.add("0,0");
                        lists.add("0,0");
                        lists.add("0,0");
                        lists.add("0,0");
                        lists.add("0,0");
                        newDoc.put("sub", lists);
//                        Log.v("test4", SignUp.name_val +" "+ SignUp.roll_no_val +" "+ SignUp.email_val +" ");

                        mongoCollection.insertOne(newDoc).getAsync(task -> {
                            if (task.isSuccess()) {
                                BsonObjectId insertedId = task.get().getInsertedId().asObjectId();
                                Log.v("EXAMPLE", "successfully inserted a document with id " + insertedId);
                            } else {
                                Log.e("EXAMPLE", "failed to insert document with: ", task.getError());
                            }
                        });
                    }
                }
            }
            else {
                Log.v("User","Failed to login"+result.getError().toString());
            }
        });
    }


    public void back_from_login(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }
    public void CreateAccountBtn(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
    }
    public void Login_click(View view) {

        checkName = findViewById(R.id.checkName);
        checkPassword = findViewById(R.id.checkPassword);
        String checkName_val = Objects.requireNonNull(Objects.requireNonNull(checkName.getEditText()).getText()).toString();
        String checkPassword_val = Objects.requireNonNull(Objects.requireNonNull(checkPassword.getEditText()).getText()).toString();

        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("collections");
        Document queryFilter  = new Document("name",checkName_val);
        queryFilter.put("password",checkPassword_val);
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Document result = task.get();
                if(result==null){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Incorrect UserName or Password",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else{
                    isLodgedIn = getSharedPreferences("act", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = isLodgedIn.edit();
                    editor.putString("isLodgedIn","yes");
                    editor.apply();

                    checkNameSrd = getSharedPreferences("srd1", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editName = checkNameSrd.edit();
                    editName.putString("Name",checkName_val);
                    editName.apply();

                    checkPasswordSrd = getSharedPreferences("srd2", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editPassword = checkPasswordSrd.edit();
                    editPassword.putString("Password",checkPassword_val);
                    editPassword.apply();

//                    Log.v("list",checkName_val+" "+checkPassword_val);

                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Lodged in successfully"+checkName_val+checkPassword_val,
                            Toast.LENGTH_SHORT);

                    toast.show();

                }
                Log.v("EXAMPLE", "successfully found a document: "+result+queryFilter);
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });


    }

}