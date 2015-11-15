package com.example.mla.mla_counter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<Product> objects;

    ProductAdapter(Context context, ArrayList<Product> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.product_selector_listview_item, parent, false);
        }

        Product p = getProduct(position);

        ((TextView) view.findViewById(R.id.product_name)).setText(p.name);

        return view;
    }

    Product getProduct(int position) {
        return ((Product)getItem(position));
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

}