import java.io.*;
import java.net.*;

public class Chopstick implements Serializable{
	
	private int id;
	public Chopstick(int i) {
		this.id=i;
	}
	
	public int getId(){
        return id;
    }
	
	@Override
	public boolean equals(Object o) {
		 
        if (o == this) {
            return true;
        }
         
        if (!(o instanceof Chopstick)) {
            return false;
        }
        Chopstick f = (Chopstick) o;
         
        return this.id == f.id;
    }
}

