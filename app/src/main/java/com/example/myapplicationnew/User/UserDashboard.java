package com.example.myapplicationnew.User;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.example.myapplicationnew.LoginSignup.Login;
import com.example.myapplicationnew.LoginSignup.SignUp;
import com.example.myapplicationnew.R;

import org.bson.BsonObjectId;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class UserDashboard extends AppCompatActivity {
    String Appid = "application-0-ovhjc";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    SharedPreferences isLodgedIn,checkNameSrd,checkPasswordSrd;
    List<String> lists;

    TextView dldm1,dldm2,seminar1,seminar2,ptrp1,ptrp2,bhr1,bhr2,coa1,coa2,os1,os2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        checkNameSrd =getSharedPreferences("srd1",MODE_PRIVATE);
        String name = checkNameSrd.getString("Name","no");

        checkPasswordSrd =getSharedPreferences("srd2",MODE_PRIVATE);
        String password = checkPasswordSrd.getString("Password","no");

        //        Hooks
        dldm1 = findViewById(R.id.dldm1);
        dldm2 = findViewById(R.id.dldm2);
        seminar1 = findViewById(R.id.seminar1);
        seminar2 = findViewById(R.id.seminar2);
        ptrp1 = findViewById(R.id.ptrp1);
        ptrp2 = findViewById(R.id.ptrp2);
        bhr1 = findViewById(R.id.bhr1);
        bhr2 = findViewById(R.id.bhr2);
        coa1 = findViewById(R.id.coa1);
        coa2 = findViewById(R.id.coa2);
        os1 = findViewById(R.id.os1);
        os2 = findViewById(R.id.os2);

        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());

//        Credentials credentials = Credentials.anonymous();
        Credentials credentials = Credentials.emailPassword("waniket48@gmail.com","Aniket@9277");
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()){
                if(!name.equals("no")) {
                    Log.v("User", "Logged in successfully_dashboard");
                    User user = app.currentUser();
                    assert user != null;
                    mongoClient = user.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("test-database");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("collections");

                    Document queryFilter = new Document("name", name);
                    queryFilter.put("password", password);
                    mongoCollection.findOne(queryFilter).getAsync(task -> {
                        if (task.isSuccess()) {
                            Document result1 = task.get();
                            lists = result1.getList("sub", String.class);

                            String[] dldm = lists.get(0).split(",");
                            String[] seminar = lists.get(1).split(",");
                            String[] ptrp = lists.get(2).split(",");
                            String[] bhr = lists.get(3).split(",");
                            String[] coa = lists.get(4).split(",");
                            String[] os = lists.get(5).split(",");


                            dldm1.setText(dldm[0]+"/"+dldm[1]);
                            seminar1.setText(seminar[0]+"/"+seminar[1]);
                            ptrp1.setText(ptrp[0]+"/"+ptrp[1]);
                            bhr1.setText(bhr[0]+"/"+bhr[1]);
                            coa1.setText(coa[0]+"/"+coa[1]);
                            os1.setText(os[0]+"/"+os[1]);
                            try {
                                dldm2.setText(((Integer.parseInt(dldm[0])) / (Integer.parseInt(dldm[1]))) + "%");
                                seminar2.setText(Integer.parseInt(seminar[0]) / Integer.parseInt(seminar[1]) + "%");
                                ptrp2.setText(Integer.parseInt(ptrp[0]) / Integer.parseInt(ptrp[1]) + "%");
                                bhr2.setText(Integer.parseInt(bhr[0]) / Integer.parseInt(bhr[1]) + "%");
                                coa2.setText(Integer.parseInt(coa[0]) / Integer.parseInt(coa[1]) + "%");
                                os2.setText(Integer.parseInt(os[0]) / Integer.parseInt(os[1]) + "%");
                            }
                            catch (Exception e){
                                dldm2.setText("0%");
                                seminar2.setText("0%");
                                ptrp2.setText("0%");
                                bhr2.setText("0%");
                                coa2.setText("0%");
                                os2.setText("0%");
                            }

                            Log.v("list", lists.toString());

                            Log.v("EXAMPLE", "successfully found a document: " + result1);
                        } else {
                            Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                        }

                    });
                }
            }
            else {
                Log.v("User","Failed to login"+result.getError().toString());
            }
        });


    }
    public void user(View view) {
        isLodgedIn =getSharedPreferences("act",MODE_PRIVATE);
        String val = isLodgedIn.getString("isLodgedIn","no");
        if (!val.equals("yes")) {
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    }
}