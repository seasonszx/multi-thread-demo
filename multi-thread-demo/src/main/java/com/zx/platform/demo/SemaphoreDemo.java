package com.zx.platform.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/***
 * 
 * @author Administrator
 * demo：信号量
 * 应用场景：多个线程占用固定个数资源时候的实现
 * 业务需求：购票（10个用户在2个窗口买票）
 */
public class SemaphoreDemo {
	
	class Task implements Runnable{
		
		private Semaphore semaphore; //信号量
		private int user; //第几个访问线程

		public Task(Semaphore semaphore, int user) {
			super();
			this.semaphore = semaphore;
			this.user = user;
		}

		public void run() {
			
			try {
				// 获取信号量许可
				semaphore.acquire();
				// 运行到这里说明，已经获得了许可，执行任务
				System.out.println("用户"+user+"执行任务！");
				Thread.sleep((long)(Math.random()*10000)); // 模拟执行时间
				System.out.println("用户"+user+"任务执行完成！");
				//释放信号量许可
				semaphore.release();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void exec() {
		// 定义信号量个数,2代表定义两个资源信号量
		Semaphore semaphore = new Semaphore(2);
		// 定义访问线程总个数
		int user = 10;
		// 定义线程池
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		for (int i = 0; i < user; i++) {
			threadPool.execute(new Task(semaphore, i));
		}
		threadPool.shutdown();
	}

	public static void main(String[] args) {
		SemaphoreDemo demo = new SemaphoreDemo();
		demo.exec();
	}

}
