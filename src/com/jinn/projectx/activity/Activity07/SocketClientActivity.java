package com.jinn.projectx.activity.Activity07;

import android.app.Activity;
import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.jinn.projectx.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 Socket通信
 */
public class SocketClientActivity extends Activity {
    private InputStream inputStream;
    private OutputStream outputStream;
    public final static String ADDRESS = "/com/jin/kai100";
    private ImageView imageView;
    LocalSocket localSocket;
    private final String TAG="SocketClientActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_07);
    }


    public void button1(View view) {
        Intent intent = new Intent(getApplicationContext(), LocalSocketService.class);
        startService(intent);
    }

    public void button2(View view) {
        localSocket = new LocalSocket();
        try {
            localSocket.connect(new LocalSocketAddress(ADDRESS));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                acceptMsg();
            }
        }).start();

    }

    public void button3(View view) {
        try {
            inputStream=localSocket.getInputStream();
            outputStream=localSocket.getOutputStream();
            outputStream.write("msg from clinet".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void button4(View view) {
        if (localSocket == null) {
            return;
        }
        try {
            localSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void acceptMsg() {
        while (true) {
            try {
                inputStream = localSocket.getInputStream();
                int totalLength = 0;
                while (totalLength == 0) {
                    totalLength = inputStream.available();
                }
                Log.d(TAG, "inputStream.length==" + totalLength);
                byte[] bytes = new byte[1024];
                int readCount = 0;
                while (readCount < totalLength) {
                    readCount = inputStream.read(bytes);
                }
                Log.d(TAG, "get msg:" + new String(bytes, 0, readCount));
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

    public void button5(View view) {
          Intent intent = new Intent(SocketClientActivity.this,AidlClientActivity.class);
          this.startActivity(intent);
    }

    public void button6(View view) {
        Intent intent = new Intent(SocketClientActivity.this,MessangerClientActivity.class);
        this.startActivity(intent);
    }

}
