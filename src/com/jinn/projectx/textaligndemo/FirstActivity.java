/**
 * 
 */
package com.jinn.projectx.textaligndemo;

import com.jinn.projectx.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author 10964493
 *
 */

public class FirstActivity extends Activity {
    private MyTextView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        view=(MyTextView)findViewById(R.id.mytextview);
        view.setText("AAAAAAA");
    }
 
    @Override
    public void onResume() {
        super.onResume();
    
    }

 
    @Override
    public void onDestroy() {
        super.onDestroy();
        

    }

}
