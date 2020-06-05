package com.jinn.projectx.activity.Activity07;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.IBinder;
import android.util.Log;

public class LocalSocketService extends Service {
    public final static String TAG = "LocalSocketService";
    public final static String ADDRESS = "/com/jin/kai100";
    LocalSocket localSocket;
    private LocalServerSocket localServerSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
        createServerSocket();
        new Thread(new Runnable() {

            @Override
            public void run() {
                acceptMsg();
            }
        }).start();
    }

    private void createServerSocket() {
        try {
            localServerSocket = new LocalServerSocket(ADDRESS);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void acceptMsg() {
        try {
            Log.d(TAG, "acceptMsg1");
            localSocket = localServerSocket.accept();
            Log.d(TAG, "acceptMsg2");
        } catch (IOException e) {

            e.printStackTrace();
        }

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
                String msgFromClient = new String(bytes, 0, readCount);
                Log.d(TAG, "get msg:" + msgFromClient);
                outputStream =localSocket.getOutputStream();
                outputStream.write("msg from server".getBytes());
                outputStream.flush();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public byte[] getInputBytes(String url) {
        URL imgUrl = null;
        InputStream is = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            is = conn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
            while ((rc = is.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

}