
public interface ChopInterf extends java.rmi.Remote {
	void grab() throws java.rmi.RemoteException;
	void release() throws java.rmi.RemoteException;
	boolean isFree() throws java.rmi.RemoteException;
}
