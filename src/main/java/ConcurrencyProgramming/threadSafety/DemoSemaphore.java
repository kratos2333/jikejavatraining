package ConcurrencyProgramming.threadSafety;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore案例：三辆小汽车抢车位
 * Semaphore信号量主要作用：1.用于多个共享资源的互斥使用，2.用于并发线程数的控制
 */
public class DemoSemaphore {
    public static void main(String[] args) {
//模拟资源类，有3个空车位
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
//占有资源
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t get the car park");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch
                    (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t left the park spot after 3 secs");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//释放资源
                    semaphore.release();
                }
            }, "Thread-Car-" + String.valueOf(i)).start();
        }
    }
}
