package com.zx.platform.demo;

/**
 * Thread.join()方法的使用
 * Thread.join()，是用来指定当前主线程等待其他线程执行完毕后，再来继续执行Thread.join()后面的代码。
 * @date 2016年1月21日
 */
public class SimpleThreadJoin implements Runnable{

	@Override
	public void run() {
		System.out.println("app start \t\t"+System.currentTimeMillis());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("app stop \t\t"+System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new SimpleThreadJoin(),"app-thread");
		t.start();
		
		try {
			t.join(3000); // join(long millis)这里参数为主线程已经等待了3000ms后执行
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("main stop \t\t"+System.currentTimeMillis());
	}
	
}