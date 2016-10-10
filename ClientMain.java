import java.util.Scanner;

public class ClientMain {
	   public static void main(String[] args) {
		      String host = "127.0.0.15";
		      int port = 2345;
			  Scanner s = new Scanner(System.in);
			  String name = null;
			  System.out.println("Please enter your name");
			  name = s.next();
			  System.out.println("Welcome to fatChat, dear " + name);
		      Thread t = new Thread(new Client(host, port, name));
		      Thread t1 = new Thread(new ClientRead(host, port, name));
		      
		      t1.start();//thread ClientRead must be started before the sender
		      t.start();//
		   }
}