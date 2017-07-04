package com.jinn.projectx.activity.main;

import com.jinn.projectx.R;
import com.jinn.projectx.activity.Activity01.TextAlignDemoActivity;
import com.jinn.projectx.activity.Activity02.WaveDemoActivity;
import com.jinn.projectx.activity.Activity03.ViewGroupActivity;
import com.jinn.projectx.activity.Activity04.HandlerThreadActivity;
import com.jinn.projectx.activity.Activity05.ThreadPoolActivity;
import com.jinn.projectx.activity.Activity06.ReflectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.button1) {
            intent.setClass(MainActivity.this, TextAlignDemoActivity.class);
            startActivity(intent);
        } else if(view.getId()==R.id.button2){
            intent.setClass(MainActivity.this, WaveDemoActivity.class);
            startActivity(intent);
        }  else if(view.getId()==R.id.button3){
            intent.setClass(MainActivity.this, ViewGroupActivity.class);
            startActivity(intent);
        }  else if(view.getId()==R.id.button4){
            intent.setClass(MainActivity.this, HandlerThreadActivity.class);
            startActivity(intent);
        }   else if(view.getId()==R.id.button5){
            intent.setClass(MainActivity.this, ThreadPoolActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.button6) {
            intent.setClass(MainActivity.this, ReflectionActivity.class);
            startActivity(intent);
        }

    }
 
}
