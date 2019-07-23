package com.icbc.emallim.threaddemo.avanced;

import java.util.concurrent.Semaphore;

public class SynchronizedBank {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        com.icbc.emallim.threaddemo.avanced.SynchronizedBank aa =new com.icbc.emallim.threaddemo.avanced.SynchronizedBank();
        Bank bank = aa.new Bank();
        //计数信号量  permits 许可证数量 ， fair 是否公平 （征用时是否为 先进先出 ）
        Semaphore semaphore = new Semaphore(1, true);
        Thread thread1 = new Thread(aa.new Transfer(bank, semaphore));
        thread1.start();
        Thread thread2 = new Thread(aa.new Transfer(bank, semaphore));
        thread2.start();
    }



    private class Transfer implements Runnable {
        private Bank bank;
        private Semaphore semaphore;

        public Transfer(Bank bank, Semaphore semaphore) {// 初始化变量
            this.bank = bank;
            this.semaphore = semaphore;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {// 循环10次向账户存钱
                try {
                    semaphore.acquire();// 获得一个许可
                    bank.deposit(10);// 向账户存入10块钱
                    System.out.print("账户的余额是：" + bank.getAccount() + "\n");
                    semaphore.release();// 释放一个许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Bank {
        private int account = 100;

        public void deposit(int money) {
            account += money;
        }

        public int getAccount() {
            return account;
        }
    }
}

