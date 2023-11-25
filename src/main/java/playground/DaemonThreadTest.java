package playground;

public class DaemonThreadTest {
    public void spawnDaemonThread() {

        Thread innerThread = new Thread(new Runnable() {

            public void run() {

                for (int i = 0; i < 100; i++) {
                    System.out.println("I am a daemon thread !");
                }
            }
        });

//        innerThread.setDaemon(true);
        innerThread.start();
        System.out.println("Main thread exiting");
    }

    public static void main(String[] args) {
        DaemonThreadTest test = new DaemonThreadTest();
        test.spawnDaemonThread();
    }
}
