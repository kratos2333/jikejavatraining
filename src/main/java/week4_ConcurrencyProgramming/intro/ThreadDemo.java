package week4_ConcurrencyProgramming.intro;

public class ThreadDemo {

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("thread");
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }
}
