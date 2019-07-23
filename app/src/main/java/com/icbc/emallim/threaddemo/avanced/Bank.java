package com.icbc.emallim.threaddemo.avanced;

public class Bank {
	private static ThreadLocal<Integer> account = new ThreadLocal<Integer>() {

		@Override
		protected Integer initialValue() {
			return 10000;
		}

	};

	public void deposit(int money) {
		account.set(account.get() + money);
	}

	public int getAccount() {
		return account.get();
	}
}
