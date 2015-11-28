package com.example.mla.mla_counter;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppSingleton {

    public DatabaseHelper dbh;
    public SharedPreferences store;
    public User user;
    public DailyProducts daily_products;

    private AppSingleton() {}

    private static class SingletonHolder {
        public static final AppSingleton instance = new AppSingleton();
    }

    public void pushDailyProductsToStore(){
        SharedPreferences.Editor editor = store.edit();
        Gson gson = new Gson();

        String products = gson.toJson(daily_products);

        editor.putString("daily_products", products);
        editor.commit();
    }

    public void extractDailyProductsFromStore(){
        Gson gson = new Gson();
        String products = store.getString("daily_products", null);
        Type type = new TypeToken<DailyProducts>(){}.getType();
        daily_products = gson.fromJson(products, type);
        if (daily_products == null || !DateUtils.isToday(daily_products.date.getTime())){
            daily_products = new DailyProducts();
            Calendar c = Calendar.getInstance();
            daily_products.date = c.getTime();
            daily_products.breakfast = new ArrayList<Product>();
            daily_products.lunch = new ArrayList<Product>();
            daily_products.dinner = new ArrayList<Product>();
            pushDailyProductsToStore();
        }
    }

    public void pushUserDataToStore(){
        SharedPreferences.Editor editor = store.edit();
        Gson gson = new Gson();

        String user_data = gson.toJson(user);

        editor.putString("user_data", user_data);
        editor.commit();
    }

    public void extractUserDataFromStore(){
        Gson gson = new Gson();
        String user_data = store.getString("user_data", null);
        Type type = new TypeToken<User>(){}.getType();
        user = gson.fromJson(user_data, type);
    }

    public void initStore(Context c){
        store = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static AppSingleton getInstance()  {
        return SingletonHolder.instance;
    }

}
