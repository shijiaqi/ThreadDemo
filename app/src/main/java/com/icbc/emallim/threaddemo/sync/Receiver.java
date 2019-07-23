package com.icbc.emallim.threaddemo.sync;

import android.util.Log;

public class Receiver implements Runnable{

    private Sender sender;// 创建一个对发送者的引用

    public Receiver(Sender sender) {// 利用构造方法初始化发送者引用
        this.sender = sender;
    }

    public void run() {
        Log.i("ThreadsMsg", "首次获取状态" + sender.isIsValid() + "\n");
        for (int i = 0; i < 5; i++) {// 接收5次商品
            while (!sender.isIsValid()) {// 如果发送者没有发送商品就进行等待
                Thread.yield();
            }
            Log.i("ThreadsMsg", "收到：" + sender.getProduct() + "\n");
            try {
                Thread.sleep(1000);// 线程休眠1秒实现动态发送的效果
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sender.setIsValid(false);// 设置卖家发送商品的状态为未发送，这样卖家就可以继续发送商品
        }
    }
}
