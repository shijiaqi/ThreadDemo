package com.icbc.emallim.threaddemo.sync;

import android.util.Log;

public class Sender implements Runnable{

    private String[] products = { "《AAAAA》", "《BBBBB》", "《CCCCC》", "《DDDDD》", "《EEEEE》" };// 模拟商品列表
    private volatile String product;// 保存一个商品名称
    private volatile boolean isValid;// 保存卖家是否发送商品的状态

    public boolean isIsValid() {// 读取状态
        return isValid;
    }

    public void setIsValid(boolean isValid) {// 设置状态 是否 有效
        this.isValid = isValid;
    }

    public String getProduct() {// 获得商品
        return product;
    }

    public void run() {
        Log.i("ThreadsMsg",  "初始状态" + isValid + "\n");
        for (int i = 0; i < 5; i++) {// 向买家发送5次商品
            while (isValid) {// 如果已经发送商品就进入等待状态，等待买家接收
                Thread.yield();
            }
            product = products[i];// 获得一件商品
            Log.i("ThreadsMsg",  "发送：" + product + "\n");
            try {
                Thread.sleep(100);// 当前线程休眠0.1秒实现发送的效果
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isValid = true;// 将状态设置为已经发送商品
        }
    }
}

