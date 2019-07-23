package com.icbc.emallim.threaddemo.base;

public class ThreadState implements Runnable{

    public synchronized void waitForASecond() throws InterruptedException {
        wait(500);    //使当前线程等待0.5秒或其他线程调用notifyo或notifyAllO方法
    }
    public synchronized void waitForYcars () throws  InterruptedException{
        wait();  //使当前线程永久等待 直到其他线程  调用 notify() 或 notifyAll();
    }

    public synchronized void notifyNow()throws InterruptedException {
        notify();  //唤醒 由调用 wait() 方法进入等待状态的线程
    }


    public void run() {
            try {

                waitForASecond();  //在新线程中运行 waitForASecond（）方法
                waitForYcars();    //在新线程中运行 waitForYcars（）方法

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

}
