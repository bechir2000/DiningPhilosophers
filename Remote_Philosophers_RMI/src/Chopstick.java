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
	
}
