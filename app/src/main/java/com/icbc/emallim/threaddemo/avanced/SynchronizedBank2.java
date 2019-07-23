package com.icbc.emallim.threaddemo.avanced;


import java.util.concurrent.atomic.AtomicInteger;



public class SynchronizedBank2 {



    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        com.icbc.emallim.threaddemo.avanced.SynchronizedBank2 aa =new com.icbc.emallim.threaddemo.avanced.SynchronizedBank2();
        Bank bank = aa.new Bank();
        Thread thread1 = new Thread(aa.new Transfer(bank));
        thread1.start();
        Thread thread2 = new Thread(aa.new Transfer(bank));
        thread2.start();

    }




    private class Transfer implements Runnable {

        private Bank bank;

        public Transfer(Bank bank) {
            this.bank = bank;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                bank.deposit(10);
                System.out.print( "账户的余额是：" + bank.getAccount() + "\n");
            }
        }
    }

    private class Bank {
        private AtomicInteger account = new AtomicInteger(100);// 创建AtomicInteger对象

        public void deposit(int money) {
            account.addAndGet(money);// 实现存钱
        }

        public int getAccount() {
            return account.get();// 实现取钱
        }
    }

}
