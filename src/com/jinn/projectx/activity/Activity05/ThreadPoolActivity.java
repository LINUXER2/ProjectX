package com.jinn.projectx.activity.Activity05;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jinn.projectx.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
* http://android.jobbole.com/82092/
* */
public class ThreadPoolActivity extends Activity {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    private ExecutorService fixedThreadPool;
    private ExecutorService singleThreadPool;
    private ExecutorService cachedThreadPool;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_05);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixedThreadPool();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleThreadPool();
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cachedThreadPool();
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduledThreadPool();
            }
        });


    }

    /*
    *  fixedThreadPool:该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，即不会再创建新的线程，也不会销毁已经创建好的线程，
    *  自始自终都是那几个固定的线程在工作，所以该线程池可以控制线程的最大并发数。
    *  */
    private void fixedThreadPool() {
        fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.d("jinn", "当先线程名 " + threadName + " index==" + index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /*
    * singleThreadExecutor 该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，
    * 等待这一个线程空闲，当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。
    * */
    private void singleThreadPool() {
        singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.d("jinn", "当前线程名 " + threadName + " index==" + index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /*
    *  cachedThreadPool创建一个可以根据实际情况调整线程池中线程的数量的线程池
    * */
    private void cachedThreadPool() {
        cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.d("jinn", "当前线程名 " + threadName + " index==" + index);
                    long time = index * 500;
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /*
    * scheduledThreadPool创建一个可以控制线程池内线程定时或周期性执行某任务的线程池。
    * */
    private void scheduledThreadPool() {
        scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        /*延迟2s执行*/
//        scheduledExecutorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                String threadName = Thread.currentThread().getName();
//                Log.d("jinn", "当前线程名 " + threadName);
//            }
//        },2, TimeUnit.SECONDS);

        /*延迟1s后，每隔2s执行一次该任务*/
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                Log.d("jinn", "当前线程名 " + threadName);
            }
        }, 1, 2, TimeUnit.SECONDS);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();   //不能继续添加任务，且当正在执行的任务结束后才会真正结束线程池
        }
        //  scheduledExecutorService.shutdownNow(); // 停止当前正在执行的task，并返回尚未执行的task list
    }
}
