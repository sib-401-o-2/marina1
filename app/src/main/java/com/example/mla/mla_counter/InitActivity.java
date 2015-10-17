package com.example.mla.mla_counter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
