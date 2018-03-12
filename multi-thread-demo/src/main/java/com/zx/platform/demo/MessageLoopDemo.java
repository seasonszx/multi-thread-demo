package com.zx.platform.demo;

/***
 * 示例有两个线程。第一个线程是每个 Java 应用程序都有的主线程。 主线程创建的Runnable 对象的MessageLoop，并等待它完成。 如果
 * MessageLoop 在归档时间内未完成任务，则主线程就中断它。 如果 MEssageLoop 在规定时间内完成任务，则正常退出
 * 
 * @author Administrator
 *
 */
public class MessageLoopDemo {

	// 显示当前执行线程的名称和信息
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s:- %s%n", threadName, message);
	}

	private static class MessageLoop implements Runnable {

		@Override
		public void run() {
			while (true) {
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("messageTh has been interrupt!");
					break;
				}
				String arr[] = { "china", "japan", "american", "frach", "germany" };
				try {
					for (int i = 0; i < arr.length; i++) {
						Thread.sleep(4000);// 暂停4000毫秒
						threadMessage(arr[i]);// 打印消息
					}
				} catch (InterruptedException e) {
					System.err.println("messageth has been sleep interrupted");
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public static void main(String[] args) {
		// 在中端MessageLoop线程(默认1小时)前先延迟一段时间（单位是毫秒）
		long patience = 5000;

		// 如果命令行参数出现
		// 设置present的时间值
		// 单位是秒

		if (args.length > 0) {
			try {
				patience = Long.parseLong(args[0]) * 1000;
			} catch (NumberFormatException e) {
				System.err.println("argument must be an interger!");
				e.printStackTrace();
				System.exit(0);
			}
		}

		threadMessage("Starting MessageLoop thread!");
		long startTime = System.currentTimeMillis();
		Thread th = new Thread(new MessageLoop(), "messageTh");
		th.start();
		threadMessage("Waiting for MessageLoop thread to finish!");

		while (th.isAlive()) {
			threadMessage("Still waiting...");

			// 最长等待1秒，给MessageLoop线程来完成
			try {
				th.join(1000);
			} catch (InterruptedException e) {
				System.err.println("main thread wait 1000ms interrupt E");
				e.printStackTrace();
			}
			long currentTime = System.currentTimeMillis();
			if (currentTime - startTime > patience && th.isAlive()) {
				threadMessage("Tired of waiting");
				th.interrupt();

				// 等待
				try {
					th.join();
				} catch (InterruptedException e) {
					System.err.println("main thread wait tired interrupt E");
					e.printStackTrace();
				}
			}
		}
		threadMessage("finally!!!");
	}

}
