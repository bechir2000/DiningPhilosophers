import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;


public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Thread[] threads = new Thread[philosophers.length];
        
        Registry registry = LocateRegistry.getRegistry("localhost", 5200);

        for (int i = 0; i < philosophers.length; i++) {
        	if (System.getSecurityManager() == null)
            	System.setSecurityManager(new SecurityManager());
        	ChopInterf leftFork = (ChopInterf)registry.lookup("fork"+i);
        	ChopInterf rightFork = (ChopInterf)registry.lookup("fork"+((i+1)%philosophers.length));


            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(leftFork, rightFork); 
            } else {
                philosophers[i] = new Philosopher(rightFork, leftFork);
            }
            
            threads[i] = new Thread(philosophers[i], "Philosopher " + (i + 1));
            threads[i].start();
            Timer timer = new Timer();
            TimeOutTask timeOutTask = new TimeOutTask(threads[i], timer);
            timer.schedule(timeOutTask, 3000);
        }
        
        try {
        	for (int i=0; i<philosophers.length; i++)
        		threads[i].join();}
        	catch(Exception e) { }
        
        for (int i = 1; i < philosophers.length+1; i++)
        {
        	System.out.println("Philosophe " + i + " a mangé " + philosophers[i-1].manger + " fois");
        }
        
    }
}