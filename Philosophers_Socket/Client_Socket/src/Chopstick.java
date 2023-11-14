import java.io.*;
import java.net.*;

public class Chopstick implements Serializable{
	
	public int id;
	public Chopstick(int i) {
		this.id=i;
	}
	
	@Override
	public boolean equals(Object o) {
        Chopstick f = (Chopstick) o;    
        return this.id == f.id;
    }
}

