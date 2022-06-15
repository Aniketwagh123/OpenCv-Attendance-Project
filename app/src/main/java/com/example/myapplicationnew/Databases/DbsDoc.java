package com.example.myapplicationnew.Databases;

//    private static final String TAG = "MainActivity";

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.BsonObjectId;
import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;
import com.example.myapplicationnew.LoginSignup.SignUp;
import com.example.myapplicationnew.R;

public class DbsDoc extends AppCompatActivity {

    String Appid = "application-0-ovhjc";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    SignUp signUp ;

    public void dbms() {
        Log.v("test3","function called");
        signUp = new SignUp();
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
                    Document newDoc = new Document("name", signUp.name_val);
                    newDoc.put("branch", signUp.branch_val);
                    newDoc.put("div", signUp.div_val);
                    newDoc.put("roll_no", signUp.roll_no_val);
                    newDoc.put("email", signUp.email_val);
                    newDoc.put("password", signUp.password_val);
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
            else {
                Log.v("User","Failed to login"+result.getError().toString());
            }
        });
    }

}
