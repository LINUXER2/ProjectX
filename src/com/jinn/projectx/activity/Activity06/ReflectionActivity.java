package com.jinn.projectx.activity.Activity06;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jinn.projectx.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*  反射调用一般分为3个步骤：http://blog.csdn.net/ichsonx/article/details/9108173
    得到要调用类的class
    得到要调用的类中的方法(Method)
    方法调用(invoke)
    */
public class ReflectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_06);
        getReflectionMethod();
    }

    private void getReflectionMethod() {

        /*
        反射出不带参数的构造函数并得到对象
        * */
        String className1 = "com.jinn.projectx.activity.Activity06.Worker";
        Class clazz1 = null;
        try {
            clazz1 = Class.forName(className1);
            Object object1 = clazz1.newInstance();
            Log.d("jinn", "object1.toString====" + object1.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }


        /*反射出带有参数的构造函数并得到对象
        * */
        String className2 = "com.jinn.projectx.activity.Activity06.Worker";
        Class clazz2 = null;
        try {
            clazz2 = Class.forName(className2);
            Constructor constructor = clazz2.getConstructor(int.class, String.class);
            Object object2 = constructor.newInstance(6, "小明");
            Log.d("jinn", "object2.toString===" + object2.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        /*获取类的私有成员变量
        * */
        String className3 = "com.jinn.projectx.activity.Activity06.Worker";
        Class clazz3 = null;
        try {
            clazz3 = Class.forName(className3);
            Field ageField = clazz3.getDeclaredField("age");
            Log.d("jinn", "ageField===" + ageField);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }



        /*
        * 获取或者更改某个对象的私有字段，即模拟get、set方法*/
        String className4 = "com.jinn.projectx.activity.Activity06.Worker";
        Class clazz4 = null;

        try {
            clazz4 = Class.forName(className4);
            Field ageField = clazz4.getDeclaredField("age");
            Constructor constructor4 = clazz4.getConstructor(int.class, String.class);
            Object object4 = constructor4.newInstance(18, "小张");
            ageField.setAccessible(true);
            Object objectAgeBefore = ageField.get(object4);
            Log.d("jinn", "objectAgeBefore==" + objectAgeBefore);

            ageField.set(object4, 9527);
            Object objectAgeAfter = ageField.get(object4);
            Log.d("jinn", "objectAgeAfter==" + objectAgeAfter);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*方法调用
        * */
        String className5 = "com.jinn.projectx.activity.Activity06.Worker";
        Class clazz5 = null;
        try {
            clazz5 = Class.forName(className5);
            Method method = clazz5.getDeclaredMethod("getName");
            method.invoke(clazz5.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
