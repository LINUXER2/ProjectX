package com.jinn.pluginmodule;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinn.projectx.activity.Utils.Logit;

;

/**
 * Created by jinnlee on 2020/4/21.
 */
public class ViewManager implements PluginCardInterface {
    public static final String TAG = "ViewManager";

    @Override
    public View getView(Context context) {
        Resources resources = context.getResources();
        int resId= resources.getIdentifier("plugin_layout","layout","com.jinn.projectx");
        Log.d(TAG, "getView,resId:"+resId);
        Logit.d(TAG,"this is a test");
        View view = LayoutInflater.from(context).inflate(resId,null);
        return view;
    }

    @Override
    public void onMovingInHiboard() {

        try {
            Thread.sleep(5000);
        }catch (Exception e){
            Log.d(TAG, "onMovingInHiboard,e:"+e.toString());
        }
        Log.d(TAG, "onMovingInHiboard");
    }

    @Override
    public void onMovingOutHiBoard() {
        Log.d(TAG, "onMovingOutHiBoard");
    }

    @Override
    public void onCardVisible() {
        Log.d(TAG, "onCardVisible");
    }

    @Override
    public void onCardInvisible() {
        Log.d(TAG, "onCardInvisible");
    }

    @Override
    public void onScrollStart() {
        Log.d(TAG, "onScrollStart");
    }

    @Override
    public void onScrollStop() {
        Log.d(TAG, "onScrollStop");
    }

    @Override
    public boolean initTitleView(ViewGroup customArea) {
        Log.d(TAG, "initTitleView");
        return false;
    }

    @Override
    public void onKeyguardLocked(boolean isLocked) {
        Log.d(TAG, "onKeyguardLocked");
    }

    @Override
    public void onDismissResult(int result) {
        Log.d(TAG, "onDismissResult");
    }

    @Override
    public void onPrivacyChange(boolean isPrivacyOpen) {

    }
}
