package com.example.mla.mla_counter;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;


public class RegistrationActivity extends AppCompatActivity{

    private String name;
    private int day, month, year;
    private int height, weight;
    private TextView date_edit;
    private Spinner height_spinner;
    private Spinner weight_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR) - 18;

        date_edit = (TextView)findViewById(R.id.date);
        date_edit.setText(Integer.toString(day)+"."+Integer.toString(month)+"."+Integer.toString(year));
        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        day = d;
                        month = m;
                        year = y;
                        date_edit.setText(Integer.toString(day)+"."+Integer.toString(month)+"."+Integer.toString(year));
                    }
                }, year, month, day);
                dp.show();
            }
        });

        Integer[] height_data = new Integer[81];
        int height = 140;

        for (int i = 0; i < 81; i++)
            height_data[i] = height++;

        ArrayAdapter<Integer> height_adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, height_data);

        height_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_spinner = (Spinner) findViewById(R.id.height);
        height_spinner.setAdapter(height_adapter);


        Integer[] weight_data = new Integer[91];
        int weight = 40;

        for (int i = 0; i < 91; i++)
            weight_data[i] = weight++;

        ArrayAdapter<Integer> weight_adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, weight_data);

        height_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_spinner = (Spinner) findViewById(R.id.weight);
        weight_spinner.setAdapter(weight_adapter);

    }

}
