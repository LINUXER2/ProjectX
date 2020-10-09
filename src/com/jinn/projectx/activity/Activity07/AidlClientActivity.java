package com.jinn.projectx.activity.Activity07;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jinn.pluginmodule.IMyAidlInterface;
import com.jinn.projectx.R;
import com.jinn.projectx.activity.Utils.Logit;

public class AidlClientActivity extends Activity {

    private Button mButton;
    private IMyAidlInterface myAidlInterface;
    private final String TAG="AidlClientActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client);
        connectServer();
        mButton = (Button)findViewById(R.id.get_remote);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "abcdefgh";
                a.replace("ab",null+"");
                try{
                    myAidlInterface.getName();
                }catch (RemoteException e){
                    Logit.i(TAG,"getData error:"+e.toString());
                }

            }
        });
    }

    private void connectServer(){
        Intent intent = new Intent();
        intent.setAction("com.jinn.pluginmodule.remoteservice");
        intent.setPackage("com.jinn.pluginmodule");
        try {
            this.bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    Logit.d(TAG,"onServiceConnected");
                    myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Logit.d(TAG,"onServiceDisconnected:"+name);
                }
            },BIND_AUTO_CREATE);
        }catch (Exception e){
            Logit.i(TAG,"connect error:"+e.toString());
        }

    }
}
