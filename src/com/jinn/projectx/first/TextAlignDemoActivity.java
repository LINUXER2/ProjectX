/**
 * 
 */
package com.jinn.projectx.first;

import com.jinn.projectx.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author 10964493
 *
 */

public class TextAlignDemoActivity extends Activity {
    private MyTextView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        view=(MyTextView)findViewById(R.id.mytextview);
        view.setText("AABBCCaabbcc");
        view.setTextSize(30);
        view.setTextAlign(MyTextView.TEXT_ALIGN_BOTTOM | MyTextView.TEXT_ALIGN_CENTER_HORIZONTAL);
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
