package com.icbc.emallim.threaddemo.base;

public class Worker implements Runnable{

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("第" + i + "次更新！");// 用户线程用来输出一些语句
        }
    }
}
