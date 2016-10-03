
public class Main {
	
	   public static void main(String[] args) {
		    
		      String host = "127.0.0.1";
		      int port = 2345;
		      
		      Server ts = new Server(host, port);
		      ts.open();
		      
		      System.out.println("Serveur initialisé.");
		      
		      for(int i = 0; i < 5; i++){
		         Thread t = new Thread(new Client(host, port, "Jean-Claude"));
		         t.start();
		      }
		   }


}
