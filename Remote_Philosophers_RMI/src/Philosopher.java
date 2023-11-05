import java.rmi.RemoteException;

public class Philosopher implements Runnable {
	
	public int number;
	public ChopInterf left;
	public ChopInterf right;
	public int manger = 0;
	
	Philosopher (ChopInterf left, ChopInterf right) {
		this.left = left;
		this.right = right;
	}
	
	private void doAction(String action) throws InterruptedException
	{
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep(((int) (Math.random() * 100)));
	}
	
	public void run() {
		try {
			while(true) {
				doAction(": Thinking");
				if(left.isFree()) {
					left.grab();
					doAction(": Picked up left fork");
					if(right.isFree()) {
						right.grab();
						doAction(": Picked up right fork - eating"); 
						manger++;
						right.release();
					}
					doAction(": Put down right fork");
					left.release();
					doAction(": Put down left fork. Back to thinking");
				}
			}
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
			return;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
