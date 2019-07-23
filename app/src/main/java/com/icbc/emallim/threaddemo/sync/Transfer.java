package com.icbc.emallim.threaddemo.sync;

import android.util.Log;

public class Transfer implements Runnable {

    private Bank2 bank;
    //private Bank bank;
    private String num;

    /**
     * 线程局部变量
     * @param bank
     * @param num
     */
    public Transfer(Bank2 bank, String num) {// 利用构造方法初始化变量
        this.bank = bank;
        this.num = num;
    }


   /* public Transfer(Bank bank, String num) {// 利用构造方法初始化变量
        this.bank = bank;
        this.num = num;
    }*/

    public void run() {
        for (int i = 0; i < 1000; i++) {// 循环1000次向账户存钱
            bank.deposit(10);// 向账户存入10块钱
            //bank.setAccount(Integer.parseInt(num));
            Log.i("threadnosync", num + "线程执行"+i+"次   账户的余额是：" + bank.getAccount() + "\n");
            //System.out.print(num + "线程执行"+i+"次   账户的余额是：" + bank.getAccount() + "\n");
        }
        Log.i("threadnosync",num+"线程结果："+bank.getAccount());
    }

}
