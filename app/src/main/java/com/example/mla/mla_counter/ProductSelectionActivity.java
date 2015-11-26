package com.example.mla.mla_counter;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductSelectionActivity extends AppCompatActivity {

    private EditText search_field;
    private Button search_button;
    private ListView product_list;
    private int meal_id;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);

        Intent intent = getIntent();
        meal_id = intent.getIntExtra("container_id",0);

        search_field = (EditText)findViewById(R.id.search_field);
        search_button = (Button)findViewById(R.id.search_btn);
        product_list = (ListView)findViewById(R.id.product_list);

        products  = new ArrayList<>();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = AppSingleton.getInstance().dbh.getProductsByName(search_field.getText().toString());
                c.moveToFirst();

                products.clear();

                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {
                        products.add(new Product(c.getString(1), c.getDouble(2), c.getDouble(3), c.getDouble(4), c.getDouble(5)));
                        c.moveToNext();
                    }
                }

                product_list.setAdapter(new ProductAdapter(ProductSelectionActivity.this, products));

            }
        });

        product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIntegerPicker(products.get(position));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void openIntegerPicker(final Product product) {
        final Dialog d = new Dialog(ProductSelectionActivity.this);
        d.setTitle("Количество в граммах");
        d.setContentView(R.layout.amount_picker);
        Button add = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);

        String[] valueSet = new String[100];

        for (int i = 0; i < 100; i++) {
            valueSet[i] = Integer.toString((i+1) * 5);
        }

        final NumberPicker amount_picker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        amount_picker.setDisplayedValues(valueSet);
        amount_picker.setMaxValue(99);
        amount_picker.setWrapSelectorWheel(true);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = (amount_picker.getValue() + 1) * 5;
                try {
                    Product chosen_product = (Product) product.clone();
                    chosen_product.gramms = amount;
                    switch (meal_id) {
                        case 0:
                            AppSingleton.getInstance().daily_products.breakfast.add(chosen_product);
                            break;
                        case 1:
                            AppSingleton.getInstance().daily_products.lunch.add(chosen_product);
                            break;
                        case 2:
                            AppSingleton.getInstance().daily_products.dinner.add(chosen_product);
                            break;
                        default:
                    }
                    Toast.makeText(ProductSelectionActivity.this, "Добавлено " + Integer.toString(amount)
                            + " грамм " + product.name, Toast.LENGTH_SHORT).show();
                    d.dismiss();
                    AppSingleton.getInstance().pushDailyProductsToStore();
                }catch(CloneNotSupportedException e){}
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
