package com.icbc.emallim.threaddemo.avanced;

import android.util.Log;

import java.util.LinkedList;

public class Producer2 implements Runnable{

    private final LinkedList<String> list = new LinkedList<String>();
    private static final int MAX = 10;
    private volatile int count;

    public void run() {
        for (int i = 0; i < MAX; i++) {// 向仓库中添加商品，MAX是仓库的最大容量
            synchronized (list) {// 使用同步块来解决同步问题
                if (list.size() == MAX) {// 如果仓库装满就等待
                    Log.i("thread_obj","仓库已空\n");
                    try {
                        list.wait();// 开始等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    String product = "Java多线程";
                    list.add(product);// 向仓库中添加商品
                    list.notify();// 唤醒等待的线程
                    Log.i("thread_obj","生产：" + product + "\n");
                    count++;// 仓库中商品的数量加一
                    Log.i("thread_obj","仓库中还有" + count + "个商品\n");
                    try {
                        Thread.sleep(100);// 当前线程休眠0.1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
