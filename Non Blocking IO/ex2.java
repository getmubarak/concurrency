import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
  
public class NIO2
{
    public static void main(String[] args) throws IOException
    {
    	 long start = System.currentTimeMillis();
         
    	RandomAccessFile aFile = new RandomAccessFile
                ("//Users//mubarak//eclipse-workspace//TSP//src//temp.txt", "r");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(inChannel.read(buffer) > 0)
        {
            buffer.flip();
            for (int i = 0; i < buffer.limit(); i++)
            {
                System.out.print((char) buffer.get());
            }
            buffer.clear(); // do something with the data and clear/compact it.
        }
        inChannel.close();
        aFile.close();
        
        // ans is the minimum weight Hamiltonian Cycle
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
}
