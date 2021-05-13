public class ThreadInterruptTest extends Thread {
   public static void main(String[] args) throws InterruptedException {
      System.out.println("Thread main started");
      Task task = new Task();
      task.start();
      sleep(500);  
      task.interrupt(); // calling interrupt() method
      task.join();
      System.out.println(currentThread().getName() + " is finished now");  
   }
}
class Task extends Thread  {
   @Override
   public void run() {
      for (int i = 0; i < 5; i++) {
         System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);
         if(Thread.interrupted()) {
            System.out.println("This thread was interruped by someone calling this Thread.interrupt()");
            System.out.println("Cancelling task running in thread " + Thread.currentThread().getName());
            System.out.println("After Thread.interrupted() call, JVM reset the interrupted value to: " + Thread.interrupted());
            break;
         }
      }
      System.out.println(currentThread().getName() + " is finished now");  
   }
}
