package ConcurrencyProgramming.threadSafety;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo06Volatile {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(demo);
            t.start();
        }
        System.out.println("count = " + demo.count);
    }

        Thread.sleep(1000);
    static class VolatileDemo implements Runnable {
        public volatile int count;
        private Lock lock = new ReentrantLock();

        //public volatile AtomicInteger count = new AtomicInteger(0);
        public void run() {
            addCount();
        }

        public void addCount() {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                count++;//但是实际情况是三条汇编指令
                lock.unlock();
            }
        }
    }
}
