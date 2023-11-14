import java.util.Timer;

public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];
        Thread[] threads = new Thread[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(rightFork, leftFork); 
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }
            
            threads[i] = new Thread(philosophers[i], "Philosopher " + (i + 1));
            threads[i].start();
            Timer timer = new Timer();
            TimeOutTask timeOutTask = new TimeOutTask(threads[i], timer);
            timer.schedule(timeOutTask, 5000);
        }
        
        try {
        	for (int i=0; i<philosophers.length; i++)
        		threads[i].join();}
        	catch(Exception e) { }
        
        for (int i = 0; i < philosophers.length; i++)
        {
        	System.out.println("Philosophe " + (i+1) + " a mangé " + philosophers[i].manger + " fois");
        }
        
    }
}