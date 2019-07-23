package com.icbc.emallim.threaddemo.sync;

public class DeadLock implements Runnable {

    public boolean flag;// 使用flag变量作为进入不同块的标志
    private static final Object o1 = new Object();
    private static final Object o2 = new Object();

    public void run() {
        String threadName = Thread.currentThread().getName();// 获得当前线程的名字
        System.out.println(threadName + ": flag = " + flag);// 输出当前线程的flag变量值
        if (flag == true) {
            synchronized (o1) {// 为o1加锁
                try {
                    Thread.sleep(1000);// 线程休眠1秒钟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + "进入同步块o1准备进入o2");// 显示进入o1块

               // System.out.println(threadName + "已经进入同步块o2");// 显示进入o2块
                synchronized (o2) {// 为o2加锁
                    System.out.println(threadName + "已经进入同步块o2");// 显示进入o2块
                }
            }
        }
        if (flag == false) {
            synchronized (o2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + "进入同步块o2准备进入o1");// 显示进入o2块
                synchronized (o1) {
                    System.out.println(threadName + "已经进入同步块o1");// 显示进入o1块
                }
            }
        }
    }
}
