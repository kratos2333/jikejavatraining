package week4_ConcurrencyProgramming.threadSafety;

public class Demo05Synchronized {
    public synchronized void increase(){
        System.out.println("synchronized method");
    }
    public void syncBlock(){
        synchronized (this){
            System.out.println("synchronized block");
        }
    }
}