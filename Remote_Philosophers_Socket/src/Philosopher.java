import java.io.*;
import java.net.*;

public class Philosopher implements Runnable {
	
	public int number;
	private Chopstick left;
	private Chopstick right;
	public int manger = 0;
	private Socket s;
	private ObjectInputStream in;
    private ObjectOutputStream out;
	
	public Philosopher (int i) throws IOException {
		s= new Socket("localhost",70);
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		number = i;
		left = null;
		right = null;
	}
	
	
	private void doAction(String action) throws InterruptedException
	{
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep((int)(Math.random()*100));
	}
	
	public void run() {
		long start = System.currentTimeMillis();
        long end = start + 5000;
        while(System.currentTimeMillis() < end) {
			try {
					doAction(": Thinking");
					out.writeObject("Taking");
					out.writeObject(number);
	                String reponse = (String) in.readObject();
	                if(reponse.equals("OK")) {
	                	left = (Chopstick) in.readObject();
	                	doAction(": Picked up left fork");
	                	right = (Chopstick) in.readObject();
	                	doAction(": Picked up right fork - Eating");
						manger++;
						doAction(": Finished Eating - Put down right fork");
						out.writeObject("Finished"); //putting back forks
	                    out.writeObject(left);
						doAction(": Put down left fork. Back to thinking");
	                    out.writeObject(right);
	                }
	                left=null;
                    right=null;
			}
			
			catch (Exception e) {e.printStackTrace();}
        }
        try {
    		System.out.println(Thread.currentThread().getName() + " " + "quits the table");
			out.writeObject("Closed");
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	
}
