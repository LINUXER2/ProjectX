package com.jinn.projectx.activity.Activity03;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import com.jinn.projectx.R;

public class ViewGroupActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("TAG","Activity dispatchTouchEvent "+ToFlag.toFlage(ev)+" ");
        boolean result = super.dispatchTouchEvent(ev);
        Log.d("TAG","Activity dispatchTouchEvent return "+result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TAG","Activity onTouchEvent,action "+ToFlag.toFlage(event)+" ");
        boolean result = super.onTouchEvent(event);
        Log.d("TAG","Activity onTouchEvent,return "+result+" ");
        return result;
    }
}