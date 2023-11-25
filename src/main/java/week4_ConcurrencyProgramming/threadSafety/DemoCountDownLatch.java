package week4_ConcurrencyProgramming.threadSafety;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch案例：6个程序猿加班
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行
 */
public class DemoCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try { TimeUnit.SECONDS.sleep(5); } catch
                (InterruptedException e) {e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\tFinish, leave the company");
                        countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        new Thread(()->{
            try {
                countDownLatch.await();//卷王也是有极限的，设置超时时间
                System.out.println(Thread.currentThread().getName()+"\tworkaholic will be leaving at last");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "7").start();
    }
}
