package com.example.mla.mla_counter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductSelectionActivity extends AppCompatActivity {

    private EditText search_field;
    private Button search_button;
    private ListView product_list;
    ArrayList<Product> products;
    ProductAdapter product_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);

        search_field = (EditText)findViewById(R.id.search_field);
        search_button = (Button)findViewById(R.id.search_btn);
        product_list = (ListView)findViewById(R.id.product_list);

        search_field.setText("Маг");

        products  = new ArrayList<>();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = AppSingleton.getInstance().dbh.getProductsByName(search_field.getText().toString());
                c.moveToFirst();

                products.clear();

                if (c.moveToFirst()) {
                    while ( !c.isAfterLast() ) {
                        products.add(new Product(c.getString(1), c.getDouble(2), c.getDouble(3), c.getDouble(4), c.getDouble(5)));
                        c.moveToNext();
                    }
                }

                product_list.setAdapter(new ProductAdapter(ProductSelectionActivity.this, products));

            }
        });

    }

}
