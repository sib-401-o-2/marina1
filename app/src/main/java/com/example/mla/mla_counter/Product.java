package com.example.mla.mla_counter;


public class Product implements Cloneable {

    public String name;
    public double protein;
    public double fat;
    public double carbohydrate;
    public double calories;
    public int gramms;

    public Product(String name, double protein, double fat, double carbohydrate, double calories){
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calories = calories;
    }

    public Product(String name, double protein, double fat, double carbohydrate, double calories, int gramms){
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.calories = calories;
        this.gramms = gramms;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
