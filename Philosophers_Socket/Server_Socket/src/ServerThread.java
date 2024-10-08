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
	
	public void run() {
		boolean terminated = false;
		while(!terminated) {
		try {
		        String msg = (String) in.readObject();
		        if (msg.equals("Taking")) // I want to eat
		        {
		        	int id = (int) in.readObject();
		        	Chopstick left=null;
		        	Chopstick right=null;
		        	if(id ==5) { //get the forks
		        		for(Chopstick f : forks){
		                    if(f.id==1){
		                        right=f;
		                    }
		                }
		        		for(Chopstick f : forks){
		                    if(f.id==id){ // to avoid circular deadlock, the last philosopher
		                        left=f;   // will take fork N°id as his left one, as opposed to the
		                    }             // other philosophers.
		                }
		        	} //get the forks
		        	else {
		        		for(Chopstick f : forks){
		                    if(f.id==id+1){
		                        left=f;
		                    }
		                }
		        		for(Chopstick f : forks){
		                    if(f.id==id){
		                        right=f;
		                    }
		                }
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
