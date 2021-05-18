import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main 
{  
	// Driver code
    public static void main2(String[] args) throws InterruptedException
    {
        long start = System.currentTimeMillis();
        int n = 2000;
  
        // Find the minimum weight Hamiltonian Cycle
        Thread threads[] = new  Thread[n];
        for(int i =0;i< n; i++)
        {
        	Runnable runnable =() -> {}; 
        	threads[i] = new Thread(runnable);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        
        // ans is the minimum weight Hamiltonian Cycle
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
     // Driver code
    public static void main(String[] args) throws InterruptedException
    {
        long start = System.currentTimeMillis();
        int n = 2000;
  
        ExecutorService executor = Executors.newFixedThreadPool(100);
        // Find the minimum weight Hamiltonian Cycle
        for(int i =0;i< n; i++)
        {
        	final int index = i;
            executor.execute(() -> {});
        }
        executor.shutdown();  
        while (!executor.isTerminated()) {   }  
        
        // ans is the minimum weight Hamiltonian Cycle
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
}
