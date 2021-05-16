import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Count implements Runnable {

    private static long processedTime = 0;
    File file;

    public Count(File fileName) {
        file = fileName;
    }

    public static void main(String[] args) throws InterruptedException {

        File file = new File("/home/workspace/enwik8");
        Runnable runnable = new Count(file);
        long startTime = System.currentTimeMillis();
        Thread thread;

        for (int i = 0; i < 50; i++) {
            thread =new Thread(runnable);
            thread.start();
            thread.join();
        }

        long elapsedTime = System.currentTimeMillis() – startTime;
        System.out.println("Threads processing time " + elapsedTime + "milliseconds");
    }

    public void run() {
        int i = 0;
        int wordCount = 0;
        
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] wordList = line.split(",");
                wordCount += wordList.length;
            }
            System.out.println("Number of words in " + Thread.currentThread().getName() + "–" + wordCount);

            reader.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
