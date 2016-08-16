package com.gome.upm.common.util;

import java.util.Date;
import java.util.Random;

public class LongIdGenUitls {

	public synchronized static Long generateLongId() {
		Date dt = new Date();
		Long time = dt.getTime();
		int i = new Random().nextInt(99999);
		String str = String.format("%05d", i);
		String s = time + str;
		return Long.valueOf(s);
	}

	public static void main(String[] args) {
		for(int i=0;i<10000;i++){
			new Thread(new Runnable(){
				public void run() {
					System.out.println(LongIdGenUitls.generateLongId());
				}				
			}).start();
		}
	}
}
