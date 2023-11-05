import java.net.*;
import java.io.*;
import java.util.Timer;

public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Thread[] threads = new Thread[philosophers.length];

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i+1); 
            threads[i] = new Thread(philosophers[i], "Philosopher " + (i + 1));
    		threads[i].start();
        }
        
    	for (int i=0; i<philosophers.length; i++)
    	{
    		threads[i].join();
    	}

    
        for (int i = 1; i < philosophers.length+1; i++)
        {
        	System.out.println("Philosophe " + i + " a mangé " + philosophers[i-1].manger + " fois");
        }
        
    }
}