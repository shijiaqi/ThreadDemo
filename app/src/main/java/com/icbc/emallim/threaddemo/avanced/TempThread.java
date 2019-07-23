package com.icbc.emallim.threaddemo.avanced;

public class TempThread implements Runnable {

    private int id = 0;

    @Override
    public void run() {// run()方法给id做自增运算
        id++;
    }
}
