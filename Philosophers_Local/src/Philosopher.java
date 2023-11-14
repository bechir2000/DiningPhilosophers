
public class Philosopher implements Runnable {
	private Object leftFork;
	private Object rightFork;
	public int manger; 
	
	public Philosopher (Object leftFork, Object rightFork)
	{
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		manger = 0;
	}
	
	private void doAction(String action) throws InterruptedException
	{
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep(30);
	}
	
	public void run()
	{
		try {
			while(true) {
				doAction(": Thinking");
				synchronized(leftFork)
				{
					doAction(": Picked up left fork");
					synchronized(rightFork)
					{
						doAction(": Picked up right fork - eating"); 
						manger++;
						doAction(": Put down right fork");
					}
					doAction(": Put down left fork. Back to thinking");
				}
			}
		} catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
			return;
		}
	}
}
