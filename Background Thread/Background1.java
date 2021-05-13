public class Background1 extends Thread{  
	 public void run(){  
	  if(Thread.currentThread().isDaemon()){//checking for daemon thread  
	   System.out.println("daemon thread work");  
	  }  
	  else{  
	  System.out.println("user thread work");  
	 }  
	 }  
	 public static void main(String[] args){  
		 Background1 t1=new Background1();//creating thread  
		 Background1 t2=new Background1();  
		 Background1 t3=new Background1();  
	  
	  t1.setDaemon(true);//now t1 is daemon thread  
	    
	  t1.start();//starting threads  
	  t2.start();  
	  t3.start();  
	 }  
	}  
