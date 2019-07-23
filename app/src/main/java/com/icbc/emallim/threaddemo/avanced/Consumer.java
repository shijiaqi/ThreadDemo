package com.icbc.emallim.threaddemo.avanced;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<Integer> queue;
    private final int size = 10;

   public  Consumer(BlockingQueue<Integer> queue){
       this.queue=queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < size; i++) {
            int b = 0;
            try {
                b = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("threadproduct","消费商品：" + b + "\n");
            Log.i("threadproduct","仓库中还有" + queue.size() + "个商品\n");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
