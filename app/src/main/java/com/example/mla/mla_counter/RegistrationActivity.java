package com.example.mla.mla_counter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;


public class RegistrationActivity extends AppCompatActivity{

    private User user_edit;
    private EditText name_edit;
    private TextView date_edit;
    private Spinner height_spinner;
    private Spinner weight_spinner;
    private RadioGroup gender_group;
    private ImageButton phys_activity_edit;
    private TextView phys_actiity_textview;
    final Calendar c = Calendar.getInstance();
    final LinkedHashMap phys_activities = new LinkedHashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (AppSingleton.getInstance().user == null){
            user_edit = new User();
        }
        else{
            user_edit = AppSingleton.getInstance().user;
        }

        name_edit = (EditText)findViewById(R.id.edit_name);
        name_edit.setText(user_edit.name);
        date_edit = (TextView)findViewById(R.id.date);
        c.setTime(user_edit.birthday);

        setDateToEditText();

        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        c.set(y,m,d);
                        setDateToEditText();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });

        Integer[] height_data = new Integer[81];
        int height = 140;

        for (int i = 0; i < 81; i++)
            height_data[i] = height++;

        final ArrayAdapter<Integer> height_adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, height_data);
        height_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_spinner = (Spinner) findViewById(R.id.height);
        height_spinner.setAdapter(height_adapter);
        height_spinner.setSelection(height_adapter.getPosition(user_edit.height));
        height_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_edit.height = height_adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Integer[] weight_data = new Integer[91];
        int weight = 40;

        for (int i = 0; i < 91; i++)
            weight_data[i] = weight++;

        final ArrayAdapter<Integer> weight_adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, weight_data);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_spinner = (Spinner) findViewById(R.id.weight);
        weight_spinner.setAdapter(weight_adapter);
        weight_spinner.setSelection(weight_adapter.getPosition(user_edit.weight));
        weight_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_edit.weight = weight_adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gender_group = (RadioGroup)findViewById(R.id.gender_container);
        if (user_edit.gender.equals("female")){
            gender_group.check(R.id.female);
        }
        else{
            gender_group.check(R.id.male);
        }
        gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    user_edit.gender = "male";
                } else user_edit.gender = "female";
            }
        });

        phys_activities.put(1.2, "Сидячий образ жизни");
        phys_activities.put(1.375, "Небольшая активность (спорт 1-3 дня в неделю)");
        phys_activities.put(1.55, "Средняя активность (спорт 3-5 дня в неделю)");
        phys_activities.put(1.725, "Высокая активность (спорт 6-7 дней в неделю)");
        phys_activities.put(1.9, "Очень высокая активность (очень активные занятия спортом каждый день," +
                " физическая активность на работе)");

        phys_actiity_textview = (TextView)findViewById(R.id.phys_activity);
        phys_actiity_textview.setText(phys_activities.get(user_edit.phys_activity_index).toString());
        phys_activity_edit = (ImageButton)findViewById(R.id.phys_activity_edit);
        phys_activity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(RegistrationActivity.this);
                CharSequence[] items = (CharSequence[])phys_activities.values().toArray(new CharSequence[phys_activities.size()]);
                final ArrayList<Double> phys_indexes = new ArrayList<>(phys_activities.keySet());
                int current_position = phys_indexes.indexOf(user_edit.phys_activity_index);
                adb.setSingleChoiceItems(items, current_position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int n) {
                        user_edit.phys_activity_index = phys_indexes.get(n);
                        phys_actiity_textview.setText(phys_activities.get(user_edit.phys_activity_index).toString());
                    }
                });
                adb.setNegativeButton("Готово", null);
                adb.setTitle("Физическая активность");
                adb.show();
            }
        });

        final Button save = (Button)findViewById(R.id.save_profile);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_edit.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Имя не может быть пустым", Toast.LENGTH_LONG).show();
                    return;
                }
                if (c.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()){
                    Toast.makeText(RegistrationActivity.this, "Вы из будущего?", Toast.LENGTH_LONG).show();
                    return;
                }
                user_edit.birthday = c.getTime();
                user_edit.name = name_edit.getText().toString();
                AppSingleton.getInstance().user = user_edit;
                AppSingleton.getInstance().pushUserDataToStore();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }

    public void setDateToEditText(){
        date_edit.setText(Integer.toString(c.get(Calendar.DAY_OF_MONTH))
                +"."+Integer.toString(c.get(Calendar.MONTH)+1)
                +"."+Integer.toString(c.get(Calendar.YEAR)));
    }

}
