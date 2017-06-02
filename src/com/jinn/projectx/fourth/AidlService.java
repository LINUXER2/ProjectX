package com.jinn.projectx.fourth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AidlService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
       
        return stub;
    }
 
    IPerson.Stub stub =new IPerson.Stub() {
        
        @Override
        public String greet(String someone) throws RemoteException {
            
            return "hello "+someone;
        }
    };

    @Override
    public void onCreate() {
        Log.d("jinn","onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("jinn","onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("jinn","onUnbind");
        return super.onUnbind(intent);
    }
    
    
}
