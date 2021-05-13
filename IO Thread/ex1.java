import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
  
public class NIO1
{
    public static void main(String[] args)
    {
        BufferedReader br = null;
        String sCurrentLine = null;
        try
        {
        	 long start = System.currentTimeMillis();
             
            br = new BufferedReader(
            new FileReader("//Users//mubarak//eclipse-workspace//TSP//src//temp.txt"));
            while ((sCurrentLine = br.readLine()) != null)
            {
                System.out.println(sCurrentLine);
            }
            // ans is the minimum weight Hamiltonian Cycle
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (br != null)
                br.close();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
