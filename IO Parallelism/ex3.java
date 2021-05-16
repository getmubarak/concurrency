import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureObject {
   public static void main(String[] args) throws Exception {
      readFile();
   }
   private static void readFile() throws IOException, InterruptedException, ExecutionException {
      String filePath = "//Users//mubarak//eclipse-workspace//TSP//src//temp.txt";
      printFileContents(filePath);
      Path path = Paths.get(filePath);		
      AsynchronousFileChannel channel =AsynchronousFileChannel.open(path, StandardOpenOption.READ);
      ByteBuffer buffer = ByteBuffer.allocate(400);
      Future<Integer> result = channel.read(buffer, 0); // position = 0
      while (! result.isDone()) {
         System.out.println("Task of reading file is in progress asynchronously.");
      }
      System.out.println("Reading done: " + result.isDone());
      System.out.println("Bytes read from file: " + result.get()); 
      buffer.flip();
      System.out.print("Buffer contents: ");
      while (buffer.hasRemaining()) {
         System.out.print((char) buffer.get());                
      }
      System.out.println(" ");
      buffer.clear();
      channel.close();
   }
   private static void printFileContents(String path) throws IOException {
      FileReader fr = new FileReader(path);
      BufferedReader br = new BufferedReader(fr);
      String textRead = br.readLine();
      System.out.println("File contents: ");
      while (textRead != null) {
         System.out.println("     " + textRead);
         textRead = br.readLine();
      }
   fr.close();
   br.close();
   }
}
