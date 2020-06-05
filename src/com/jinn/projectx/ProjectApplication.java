package com.jinn.projectx;

import android.app.Application;

import com.jinn.projectx.activity.Utils.Logit;

/**
 * Created by jinnlee on 2020/5/28.
 */
public class ProjectApplication extends Application {
    private static final String TAG="Application";
    @Override
    public void onCreate() {
        super.onCreate();
        Logit.d(TAG,"onCreate:");
    }
}
