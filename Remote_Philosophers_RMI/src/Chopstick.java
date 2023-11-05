import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Chopstick implements ChopInterf {
	
	private boolean Free = true;
	public synchronized void grab() {
			Free=false;
	}
	
	public synchronized void release() {
			Free=true;
	}
	
	public synchronized boolean isFree() {
		return this.Free;
	}
	
	public static void main(String[] args) throws RemoteException
	{
		if (System.getSecurityManager() == null) 
			System.setSecurityManager(new RMISecurityManager());
		
        Registry registry = LocateRegistry.getRegistry(5200);
        for (int i=0; i<5; i++) {
        	Chopstick fork = new Chopstick();
        	ChopInterf stub = (ChopInterf)UnicastRemoteObject.exportObject(fork,0);
			registry.rebind("fork" + i, stub);
        }
	}
}
