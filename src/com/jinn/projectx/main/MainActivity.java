package com.jinn.projectx.main;

import com.jinn.projectx.R;
import com.jinn.projectx.first.TextAlignDemoActivity;
import com.jinn.projectx.second.WaveDemoActivity;
import com.jinn.projectx.third.ViewGroupActivity;

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
        }     

    }
 
}
