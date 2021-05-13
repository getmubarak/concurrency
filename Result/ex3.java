//You can use classes from java.util.concurrent.atomic like AtomicInteger.

public static void main(String[] args) throws InterruptedException {
    AtomicInteger value = new AtomicInteger(0);
    Thread thread = new Thread(() -> value.set(2));
    thread.start();
    thread.join();
    System.out.println(value.get());
}
