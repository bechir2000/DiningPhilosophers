import java.net.*;
import java.io.*;
import java.util.*;

public class ServerThread extends Thread {
	private Socket socket;
	Set<Chopstick> forks = new HashSet<>();
	ObjectOutputStream out;
	ObjectInputStream in;
	
	public ServerThread(ServerSocket ss, Set<Chopstick> forks) {
		try {
			this.socket = ss.accept();
			this.out = new ObjectOutputStream(socket.getOutputStream());
		    this.in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.forks = forks;	
	}
	
	private Chopstick sendFork(int id){
        for(Chopstick f : forks){
            if(f.getId()==id){
                return f;
            }
        }
        return null;
    }
	
	public void run() {
		boolean terminated = false;
		while(!terminated) {
		try {
		        String msg = (String) in.readObject();
		        if (msg.equals("Taking")) // I want to eat
		        {
		        	int id = (int) in.readObject();
		        	Chopstick left;
		        	Chopstick right;
		        	if(id ==5) {
		        		right=sendFork(id);
			        	left=sendFork(1);
		        	}
		        	else {
			        	left=sendFork(id+1);
			        	right=sendFork(id);
		        	}
		        	if (forks.contains(left) && forks.contains(right)) {
		        		out.writeObject("OK");
		        		out.writeObject(left);
		        		out.writeObject(right);
		        		forks.remove(left);
		        		forks.remove(right);
		        	}
		        	else {
                        out.writeObject("not OK");
                        }
		        }
		        else if (msg.equals("Finished")) {
		        	Chopstick fork1 = (Chopstick) in.readObject();
		        	Chopstick fork2 = (Chopstick) in.readObject();
                    forks.add(fork1); 
                    forks.add(fork2); 
		        }
				else if(msg.equals("Closed")){
		            terminated=true;
		        }   
			 }
		
	    catch(Exception e) {e.printStackTrace();}
		}
  }	
}
