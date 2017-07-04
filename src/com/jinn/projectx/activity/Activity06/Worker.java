package com.jinn.projectx.activity.Activity06;

import android.util.Log;

/**
 * Created by jinnlee on 2017/7/3.
 */

public class Worker {
    private int age;
    private String name;

    public Worker() {
        super();
      Log.d("jinn","public Worker()");
    }

    public Worker(int age, String name) {
        super();
        this.age = age;
        this.name = name;
       Log.d("jinn","public Worker(int age, String name)");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Worker [age=" + age + ", name=" + name + "]";
    }

    public void printMessage(String name, int age, int salary) {
        System.out.println("name=" + name + ",age=" + age + ",salary=" + salary);
    }
}
