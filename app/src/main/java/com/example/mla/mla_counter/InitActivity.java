package com.example.mla.mla_counter;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;

import java.io.IOException;

public class InitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        AppSingleton.getInstance().dbh = new DatabaseHelper(this);
        AppSingleton.getInstance().initStore(this);

        try {
            AppSingleton.getInstance().dbh.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            AppSingleton.getInstance().dbh.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        AppSingleton.getInstance().extractUserDataFromStore();
        AppSingleton.getInstance().extractDailyProductsFromStore();

        if (AppSingleton.getInstance().user == null){
            startActivity(new Intent(this, RegistrationActivity.class));
        }
        else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
