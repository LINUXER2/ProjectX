/**
 *
 */
package com.jinn.projectx.activity.Activity04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinn.projectx.R;


/**
 * @author 好大一个坑
 *         handlerThread 方便在子线程与子线程之前通信，UI线程与子线程通信
 *         handler 实现UI线程与UI线程，子线程与UI线程通信
 *         https://carson-ho.github.io/2016/08/15/HandlerThread/
 */
public class HandlerThreadActivity extends Activity {
    private TextView mTextView;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private Handler mWorkHandler;
    private Handler mUIHandler=new Handler();
    private HandlerThread mHandlerThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_04);
        initBackground();
        initViews();
    }

    private void initViews() {
        mTextView = (TextView) findViewById(R.id.textView1);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkHandler.sendEmptyMessage(0);   //ui线程通過Handler向子线程发送消息，消息排隊等待處理
                Intent intent = new Intent();
                intent.setAction("action_direct_move_to_hiboard");
                intent.putExtra("card_type", 12);         //cardType为卡片id，不同卡片id由负一屏提供
                HandlerThreadActivity.this.sendBroadcast(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkHandler.sendEmptyMessage(1);    //UI线程通過Handler向子线程发送消息 ，消息排隊等待處理
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerThread.quit();
            }
        });
    }

    private void initBackground() {
        mHandlerThread = new HandlerThread("thread_1");
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("jinn", "handlerMsg:" + msg.toString() + "  ThreadName==" + Thread.currentThread().getName());
                switch (msg.what) {
                    case 0:
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mUIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("延迟2秒后显示：我爱学习");
                            }
                        });
                        break;
                    case 1:
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mUIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText("延迟4秒显示：我不爱学习");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onStart()
     */
    @Override
    public void onStart() {
        super.onStart();
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {
        super.onPause();
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    public void onStop() {
        super.onStop();
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO Auto-generated method stub

    }

}
