package com.icbc.emallim.threaddemo.base;

import android.util.Log;

public class EmergencyThread implements Runnable{

    @Override
    public void run() {
        for (int i = 1; i < 6; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("threadjoin","插队线程运行" + i);
        }
    }
}
