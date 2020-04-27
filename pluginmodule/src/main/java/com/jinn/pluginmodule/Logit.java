package com.jinn.projectx.activity.Utils;

import android.util.Log;

/**
 * Created by jinnlee on 2020/4/21.
 */
public class Logit {
    public static void d(String tag,String msg){
        Log.d(tag,msg+":plugin");
    }

    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }
}
