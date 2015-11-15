package com.example.mla.mla_counter;


public class AppSingleton {

    public DatabaseHelper dbh;

    private AppSingleton() {}

    private static class SingletonHolder {
        public static final AppSingleton instance = new AppSingleton();
    }

    public static AppSingleton getInstance()  {
        return SingletonHolder.instance;
    }

}
