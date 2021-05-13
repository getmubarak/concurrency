1. The thread that needs to return the value uses the Callable interface to implement the call method;
2. Before getting the future object, you can use the isDone() method to check whether the future is complete.
After that, you can call the get() method to get the value of the future. If you call the get() method directly, 
the get() method will block until the end of the thread.

import java.util.concurrent.*;

public class Test {

    private static final Integer SLEEP_MILLS = 3000;
    private static final Integer RUN_SLEEP_MILLS = 1000;
    private int afterSeconds = SLEEP_MILLS / RUN_SLEEP_MILLS;

    // Thread pool (based on the number of cores on the machine)
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private void testCallable() throws InterruptedException {
        Future<String> future = null;
        try {
            /**
             * Runnable has no return value. To get the return value, implement interface called able
             */
            future = fixedThreadPool.submit(() -> {
                Thread.sleep(SLEEP_MILLS);
                return "The thread returns value.";
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (future == null) return;

        for (;;) {
             if (future.isDone()) {
                try {
                    System.out.println(future.get()); //blocking call
                    break;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("After " + afterSeconds-- + " seconds,get the future returns value.");
                Thread.sleep(1000);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Test().testCallable();
    }
}
