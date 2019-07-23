package com.icbc.emallim.threaddemo.avanced;

import android.util.Log;

import java.util.LinkedList;

public class Consumer2 implements Runnable{

    private final LinkedList<String> list = new LinkedList<String>();
    private static final int MAX = 10;
    private volatile int count;

    public void run() {
        for (int i = 0; i < MAX; i++) {
            synchronized (list) {
                if (list.size() == 0) {
                    Log.i("thread_obj","仓库已空\n");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("thread_obj", list.removeLast() + "\n");
                    list.notify();
                    count--;
                    Log.i("thread_obj",  "仓库中还有" + count + "个商品\n");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}

