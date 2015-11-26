package com.example.mla.mla_counter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private User user;
    private TextView required_amount;
    private TextView current_amount;
    private TableLayout breakfast_table;
    private TableLayout lunch_table;
    private TableLayout dinner_table;
    private TextView protein_label;
    private TextView fat_label;
    private TextView carbohydrate_label;
    private TextView date;
    private ImageButton breakfast_add;
    private ImageButton lunch_add;
    private ImageButton dinner_add;
    private PieChart pie_chart;
    private LayoutInflater lInflater;
    private DailyProducts daily_products;
    private double calories_all;
    private double protein;
    private double fat;
    private double carbohydrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        user = AppSingleton.getInstance().user;
        daily_products = AppSingleton.getInstance().daily_products;

        date = (TextView)findViewById(R.id.date);
        required_amount = (TextView)findViewById(R.id.required_amount);
        current_amount = (TextView)findViewById(R.id.current_amount);
        breakfast_table = (TableLayout)findViewById(R.id.breakfast_table);
        lunch_table = (TableLayout)findViewById(R.id.lunch_table);
        dinner_table = (TableLayout)findViewById(R.id.dinner_table);
        protein_label = (TextView)findViewById(R.id.protein_label);
        fat_label = (TextView)findViewById(R.id.fat_label);
        carbohydrate_label = (TextView)findViewById(R.id.carbohydrate_label);
        breakfast_add = (ImageButton)findViewById(R.id.add_breakfast_btn);
        lunch_add = (ImageButton)findViewById(R.id.add_lunch_btn);
        dinner_add = (ImageButton)findViewById(R.id.add_dinner_btn);

        ImageButton[] add_product_btns = {breakfast_add, lunch_add, dinner_add};

        for (int i=0; i<3; i++){
            final int meal_id = i;
            add_product_btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ProductSelectionActivity.class);
                    intent.putExtra("container_id", meal_id);
                    startActivity(intent);
                }
            });
        }

        pie_chart = (PieChart) findViewById(R.id.piechart);

        refreshData();
    }

    private int countDailyAmountOfCalories() {
        Calendar c = Calendar.getInstance();
        int current_year = c.get(Calendar.YEAR);
        c.setTime(user.birthday);
        int age = current_year - c.get(Calendar.YEAR);
        double amount = 10*user.weight + 6.25*user.height - 5*age;
        if (user.gender.equals("male")){
            amount += 5;
        }
        else{
            amount -= 161;
        }
        amount *= user.phys_activity_index;
        return (int)amount;
    }

    public void refreshData(){
        setTitle(user.name);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date current_date = new Date();
        date.setText(dateFormat.format(current_date));

        required_amount.setText(Integer.toString(countDailyAmountOfCalories()) + " ккал");

        breakfast_table.removeAllViews();
        lunch_table.removeAllViews();
        dinner_table.removeAllViews();

        calories_all = 0;
        protein = 0;
        fat = 0;
        carbohydrate = 0;

        for (Product p : daily_products.breakfast){
            breakfast_table.addView(getRowWithProduct(p));
            collectProductData(p);
        }

        for (Product p : daily_products.lunch){
            lunch_table.addView(getRowWithProduct(p));
            collectProductData(p);
        }

        for (Product p : daily_products.dinner){
            dinner_table.addView(getRowWithProduct(p));
            collectProductData(p);
        }

        current_amount.setText(Integer.toString((int)calories_all) + " ккал");

        pie_chart.clearChart();
        pie_chart.addPieSlice(new PieModel("Белки", (int) protein, Color.parseColor("#5CB8D6")));
        pie_chart.addPieSlice(new PieModel("Жиры", (int)fat, Color.parseColor("#EB6073")));
        pie_chart.addPieSlice(new PieModel("Углеводы", (int)carbohydrate, Color.parseColor("#60EB95")));
        pie_chart.startAnimation();

        protein_label.setText(Html.fromHtml("<font color=\"#5CB8D6\">Белки: " + Integer.toString((int) protein) + " грамм</font>"));
        fat_label.setText(Html.fromHtml("<font color=\"#EB6073\">Жиры: " + Integer.toString((int)fat) + " грамм</font>"));
        carbohydrate_label.setText(Html.fromHtml("<font color=\"#60EB95\">Углеводы: " + Integer.toString((int)carbohydrate) + " грамм</font>"));
        pie_chart.startAnimation();
    }

    public void collectProductData(Product product){
        double index = product.gramms / 100.0;
        protein += (product.protein * index);
        fat += (product.fat * index);
        carbohydrate += (product.carbohydrate * index);
        calories_all += (product.calories * index);
    }

    public View getRowWithProduct(Product product){
        View view = lInflater.inflate(R.layout.product_row, null);
        ((TextView) view.findViewById(R.id.product_name)).setText(product.name);
        ((TextView) view.findViewById(R.id.product_amount)).setText(product.gramms + " г");
        return view;
    }

    @Override
    public void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void editProfile(MenuItem item){
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
