package com.icbc.emallim.threaddemo.avanced;


public class DiningPhilosophers{


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        ChopstickArray chopstickArray = new ChopstickArray(5);
        for(int i=0;i<5;i++) {
            new Thread(new Philosopher(i, chopstickArray)).start();
        }
	}

}
