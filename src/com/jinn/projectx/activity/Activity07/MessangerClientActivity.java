package com.jinn.projectx.activity.Activity07;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jinn.projectx.R;
import com.jinn.projectx.activity.Utils.HeavyWorkThread;

/**
 * Messanger 跨进程通信,客户端
 * 1.链接远端service
 * 2.获取远端回传的mMessengerToRemote
 * 3.通过mMessengerToRemote发送消息，同时指定消息回传的接收方
 * 4.new一个 mMessengerClient，用户处理源端回传的消息
 */

public class MessangerClientActivity extends Activity {

    private Button mButtonCollect;
    private Button mButtonGetData;
    private Messenger mMessengerToRemote;   // 远端messenger，service连接时获取
    private Messenger mMessengerClient;   // 客户端messenger，用于处理远端传过来的消息，实现双向通信
    private ServiceConnection mServiceCollection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messanger_client);
        mButtonCollect = (Button)findViewById(R.id.collect_remote_mess);
        mButtonGetData = (Button)findViewById(R.id.get_remote_mess);
        mButtonGetData.setOnClickListener(mClickListener);
        mButtonCollect.setOnClickListener(mClickListener);
        mServiceCollection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("jinn","client,onServiceConnected,"+name.getClassName());
                mMessengerToRemote = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("jinn","client,onServiceDisconnected,"+name.getClassName());
            }
        };
        initMessengerClient();

    }

    private void initMessengerClient(){
        mMessengerClient = new Messenger(new Handler(HeavyWorkThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d("jinn","client,handleMessage,msg="+msg.what);
                switch (msg.what){
                    case 10:
                        break;
                    case 11:
                        break;
                }
                super.handleMessage(msg);
            }
        });
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.collect_remote_mess:
                    Intent intent = new Intent();
                    intent.setPackage("com.jinn.pluginmodule");
                    intent.setAction("com.jinn.pluginmodule.remotemessengerservice");
                    MessangerClientActivity.this.bindService(intent,mServiceCollection,BIND_AUTO_CREATE);
                    break;
                case R.id.get_remote_mess:
                    Message message = Message.obtain(null,1);
                    Bundle data = new Bundle();
                    data.putString("requestId","123456");
                    message.setData(data);
                    message.replyTo = mMessengerClient;    // 如果需要接受远端回传消息，在这里指定接受消息的messenger；
                    try {
                        mMessengerToRemote.send(message);
                    }catch (RemoteException e){
                        Log.d("jinn","client,RemoteException:"+e.toString());
                    }
                    break;
                    default:
                        break;
            }
        }
    };


}
