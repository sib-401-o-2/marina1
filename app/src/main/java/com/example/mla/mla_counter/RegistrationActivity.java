package com.example.mla.mla_counter;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity{

    EditText date_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        date_edit = (EditText)findViewById(R.id.date);
        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_edit.setText("Дата рождения "+Integer.toString(dayOfMonth)+"."+Integer.toString(monthOfYear)+"."+Integer.toString(year));
                    }
                }, 2000, 10, 13);
                dp.show();
            }
        });

    }

}
