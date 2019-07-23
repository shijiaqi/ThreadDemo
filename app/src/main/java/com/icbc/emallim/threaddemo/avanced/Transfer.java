package com.icbc.emallim.threaddemo.avanced;

import android.util.Log;

import java.util.concurrent.Callable;


public class Transfer implements Callable<Integer> {
    private Bank bank;
    private String num;

    public Transfer(Bank bank, String num) {// 利用构造方法初始化变量
        this.bank = bank;
        this.num=num;
    }

    public Integer call() {
        for (int i = 0; i < 1000; i++) {// 循环10次向账户中存钱
            bank.deposit(10);
            Log.i("threadreturn", num+"线程执行  "+"账户的余额是：" + bank.getAccount() + "\n");
        }
        return bank.getAccount();// 获得账户的余额
    }
}
