package com.example.mla.mla_counter;

import java.util.Calendar;
import java.util.Date;

public class User {

    public String name;
    public int height;
    public int weight;
    public Date birthday;
    public String gender;
    public double phys_activity_index;

    public User() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -18);
        this.name = "";
        this.height = 170;
        this.weight = 60;
        this.birthday = c.getTime();
        this.gender = "male";
        this.phys_activity_index = 1.375;
    }
}
