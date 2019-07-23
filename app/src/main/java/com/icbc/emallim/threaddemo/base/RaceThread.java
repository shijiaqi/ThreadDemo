package com.icbc.emallim.threaddemo.base;

import android.util.Log;

public class RaceThread {

    public class Rabbit implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i < 11; i++) {// 循环10次模拟赛跑的过程

                try {
                    Thread.sleep(1000);// 线程休眠1秒，模拟兔子在跑步
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("threadsleep","兔子跑了" + i + "0米\n");// 显示兔子的跑步距离
                if (i == 9) {
                    Log.i("threadsleep","兔子在睡觉\n");
                    try {
                        Thread.sleep(15000);// 线程休眠15秒，模拟兔子在睡觉
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (i == 10) {
                    try {
                        Thread.sleep(1000);// 线程休眠0.001秒，模拟兔子在跑步
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("threadsleep","兔子到达终点\n"); // 显示兔子到达了终点
                }
            }
        }
    }

    public class Tortoise implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i < 11; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("threadsleep", "乌龟跑了" + i + "0米\n");
                if (i == 10) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("threadsleep", "乌龟到达终点\n");
                }
            }

        }

    }
}
