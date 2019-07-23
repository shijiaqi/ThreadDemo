package com.icbc.emallim.threaddemo.base;

import android.util.Log;

public class CounterThread implements Runnable{

    private int count = 0;
    private boolean stopped = true;

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    @Override
    public void run() {

        while (stopped) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("threadstop","第" + (count++) + "次更新！");
        }

        Log.i("threadstop","线程执行结束");


    }
}
