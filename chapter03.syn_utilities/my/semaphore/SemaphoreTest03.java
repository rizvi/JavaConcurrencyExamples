package my.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
public class SemaphoreTest03 {
	public static void main(String[] args) {
		MyPool myPool = new MyPool(20);
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		MyThread t1 = new MyThread("任务A", myPool, 3);
		MyThread t2 = new MyThread("任务B", myPool, 12);
		MyThread t3 = new MyThread("任务C", myPool, 7);
		threadPool.execute(t1);
		threadPool.execute(t2);
		threadPool.execute(t3);
		threadPool.shutdown();
	}
}

/**
 * 一个池
 */
class MyPool {
	private Semaphore sp;MyPool(int size) {
		this.sp = new Semaphore(size);
	}

	public Semaphore getSp() {
		return sp;
	}

	public void setSp(Semaphore sp) {
		this.sp = sp;
	}
}

class MyThread extends Thread {
	private String threadname; // 
	private MyPool pool; //
	private int x; // 

	MyThread(String threadname, MyPool pool, int x) {
		this.threadname = threadname;
		this.pool = pool;
		this.x = x;
	}

	public void run() {
		try {
			pool.getSp().acquire(x);
			System.out.println(threadname + "成功获取了" + x + "个许可！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			pool.getSp().release(x);
			System.out.println(threadname + "释放了" + x + "个许可！");
		}
	}
}