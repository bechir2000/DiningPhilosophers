import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Server {
	public static void main(String[] args) throws IOException, InterruptedException {
		Set<Chopstick> forks = new HashSet<> ();
		Thread[] sT = new Thread[5];
		
		for(int i=1; i<=5; i++)
		{
			forks.add(new Chopstick(i));		
		}
		
		ServerSocket ss = new ServerSocket(60);
		for(int i=0; i<5; i++) {
			sT[i] = new ServerThread(ss,forks);
		}
		
		for(int i=0; i<5; i++) {
			sT[i].start();
		}
		
		for(int i=0; i<5; i++){
            sT[i].join();
        }
		ss.close();
	}
}
