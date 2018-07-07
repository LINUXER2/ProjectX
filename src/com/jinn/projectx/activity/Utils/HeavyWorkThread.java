package com.jinn.projectx.activity.Utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * Created by jinnlee on 2018/7/7.
 */

public class HeavyWorkThread {
    private static Handler sHandler;
    private static HandlerThread sHandlerThread;
    private static final String TAG = "HeavyWorkThread";
    private static int sId = 0;

    public static Handler getHandler() {
        if (sHandlerThread == null) {
            String name = TAG + sId;
            sHandlerThread = new HandlerThread(name);
            sHandlerThread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
            sHandlerThread.start();
            sId++;
        }
        if (sHandler == null) {
            sHandler = new Handler(sHandlerThread.getLooper());
        }
        return sHandler;
    }

    public static Looper getLooper() {
        return sHandler.getLooper();
    }

    public static void reset() {
        if (sHandler != null) {
            sHandler.removeCallbacksAndMessages(null);
            sHandler = null;
        }
        if (sHandlerThread != null) {
            sHandlerThread.quit();
            sHandlerThread = null;
        }
    }
}
