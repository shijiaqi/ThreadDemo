package com.icbc.emallim.threaddemo.avanced;

import java.util.Random;


public class Philosopher implements Runnable {
    private int id;
    private ChopstickArray chopstickArray;
    private boolean state;  //false 表示在思考  
    public Philosopher(int id, ChopstickArray chopstickArray) {
        this.id = id;
        this.chopstickArray = chopstickArray;
    }

    public synchronized void thinking(){
        if (state) {
            chopstickArray.get(id).setAvailable(true);
            chopstickArray.getLast(id).setAvailable(true);
            System.out.print(" 在思考\n");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = false;
    }

    public synchronized void eating() {
        if (!state) {// state是一个布尔值，true表示哲学家刚才的状态是吃饭，false表示思考
            if (chopstickArray.get(id).isAvailable()) {// 如果哲学家右手边的筷子可用
                if (chopstickArray.getLast(id).isAvailable()) {// 如果哲学家左手边的筷子可用
                    chopstickArray.get(id).setAvailable(false);// 设置右手筷子不可用
                    chopstickArray.getLast(id).setAvailable(false);// 设置左手筷子不可用  上一个人的右手边

                    // 显示哲学家在吃饭
                    System.out.print(" 在吃饭\n");
                    try {
                        Thread.sleep(100);// 吃饭时间设置成0.1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {// 如果哲学家左手边的筷子不可用，就在相应的文本域中显示等待信息

                    System.out.print( " 在等待 " + chopstickArray.getLast(id) + "\n");
                    try {
                        wait(new Random().nextInt(100));// 等待小于0.1秒时间后检查筷子是否可用
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {// 如果哲学家右手边的筷子不可用，就在相应的文本域中显示等待信息

                System.out.print(" 在等待 " + chopstickArray.get(id) + "\n");
                try {
                    wait(new Random().nextInt(100));// 等待小于0.1秒时间后检查筷子是否可用
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        state = true;// 设置state的值为true表示哲学家的状态是吃饭
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            thinking();
            eating();
        }
    }

    @Override
    public String toString() {
        return " 哲学家 " + id;
    }
    
}
