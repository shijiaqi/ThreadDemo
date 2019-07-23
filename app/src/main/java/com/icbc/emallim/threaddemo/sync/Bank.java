package com.icbc.emallim.threaddemo.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private int account = 10000;// 假设账户的初始金额是10000
    //private volatile int account = 10000;// 将域变量用volatile修饰  多用于boolean 类型
    private Lock lock = new ReentrantLock();// 创建重入锁对象




    /**
     * 普通读写
     * @param money
     */
  /*  public  void deposit(int money) {// 向账户存钱的方法
        account += money;
    }*/

    /**
     * 方法线程同步
     * @param money
     */
   /* public synchronized void deposit(int money) {// 向账户存钱的方法
        account += money;
    }*/

    /**
     * 使用同步代码块
     * @param money
     */
 /*  public  void deposit(int money) {// 向账户存钱的方法
         synchronized (this) {// 获得Bank类的锁
            account += money;
        }
    }
*/

    /**
     * 使用重入锁
     * @param money
     */
    public void deposit(int money) {
        lock.lock();// 打开锁
        try {
            account += money;
        } finally {
            lock.unlock();// 关闭锁
        }
    }

    public int getAccount() {// 查看余额
        return account;
    }

}
