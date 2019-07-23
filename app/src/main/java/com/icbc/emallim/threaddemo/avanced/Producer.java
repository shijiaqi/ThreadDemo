package com.icbc.emallim.threaddemo.avanced;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    private BlockingQueue<Integer> queue ;
    private final int size = 10;

    public Producer(BlockingQueue<Integer> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < size; i++) {// size是域变量，表示添加商品的次数
            int b = new Random().nextInt(255); // 生成一个随机数
            Log.i("threadproduct","生产商品：" + b + "\n");
            try {
                queue.put(b);// 向队列中添加元素
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("threadproduct","仓库中还有" + queue.size() + "个商品\n");
            try {
                Thread.sleep(100);// 休眠0.1秒实现动态效果
            } catch (InterruptedException ex) {
            }
        }
    }


}
