import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

public class Main {
   public static void main(String[] args) throws Exception {
      readFile();
      Thread.sleep(10000);
   }
   private static void readFile() throws IOException, InterruptedException, ExecutionException {
      String filePath = "//Users//mubarak//eclipse-workspace//TSP//src//temp.txt";
      Path path = Paths.get(filePath);		
      AsynchronousFileChannel channel =AsynchronousFileChannel.open(path, StandardOpenOption.READ);
      ByteBuffer buffer = ByteBuffer.allocate(400);
      
      channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
    	    @Override
    	    public void completed(Integer result, ByteBuffer attachment) {
    	    	System.out.println("result = " + result);

    	        attachment.flip();
    	        byte[] data = new byte[attachment.limit()];
    	        attachment.get(data);
    	        System.out.println(new String(data));
    	        attachment.clear();
    	    }

    	    @Override
    	    public void failed(Throwable exc, ByteBuffer attachment) {

    	    }
    	});
    }
}
