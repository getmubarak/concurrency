public class JavaStopExp extends Thread  
{    
    public void run()  
    {    
        for(int i=1; i<5; i++)  
        {    
            try  
            {  
                // thread to sleep for 500 milliseconds  
                sleep(500);  
                System.out.println(Thread.currentThread().getName());    
            }catch(InterruptedException e){System.out.println(e);}    
            System.out.println(i);    
        }   
        System.out.println(currentThread().getName() + " is finished now");  
    }    
    public static void main(String args[]) throws InterruptedException  
    {    
        // creating three threads   
        JavaStopExp t1=new JavaStopExp ();    
       // JavaStopExp t2=new JavaStopExp ();   
        //JavaStopExp t3=new JavaStopExp ();   
        // call run() method   
        t1.start();  
        //t2.start();  
        // stop t3 thread   
        Thread.sleep(2000);
        t1.stop();  
        t1.join();
        System.out.println(currentThread().getName() + " is finished now");  
    }    
}  
