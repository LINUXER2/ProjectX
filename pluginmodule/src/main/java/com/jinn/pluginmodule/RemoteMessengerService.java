package com.jinn.pluginmodule;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.jinn.pluginmodule.utils.HeavyWorkThread;


/**
 * Messenger 服务端,实现双向通信
 */
public class RemoteMessengerService extends Service {
    private Messenger mMessengerRemote; // 用于接收客户端发来的信息

    private Messenger mMessengerToClient;// 用于向客户端返回信息


    public RemoteMessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
           return getMessenger().getBinder();
    }

    private Handler mHandler = new Handler(HeavyWorkThread.getLooper()){
        @Override
        public void handleMessage(Message msg) {
            Log.d("jinn","remote,handleMessage:"+msg.what);
            switch (msg.what){
                case 1:
                    String data = msg.getData().getString("requestId");
                    Log.d("jinn","remote:getMessage"+data);
                    Message message = Message.obtain();
                    message.what = 10;
                    Bundle bundle = new Bundle();
                    bundle.putString("replyId","10");
                    message.setData(bundle);
                    mMessengerToClient =msg.replyTo;
                    try {
                        mMessengerToClient.send(message);
                    }catch (RemoteException e){
                        Log.d("jinn","remote,exception:"+e.toString());
                    }

                    break;
                case 2:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Messenger getMessenger(){
        if(mMessengerRemote ==null){
            mMessengerRemote = new Messenger(mHandler);
        }
        return mMessengerRemote;
    }
}
