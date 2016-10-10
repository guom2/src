import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientRead implements Runnable{
	   private Socket connexion = null;
	   private PrintWriter writer = null;
	   private BufferedInputStream reader = null;
	   private String username;
	   private boolean isconnected=true;
   

      public ClientRead(String host, int port,String name){
	      try {
	    	 username = name;
	         connexion = new Socket(host, port);
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
      }   
   
   public void run(){
	   	int i=0;
	        while(isconnected){
		         try {
		        	String commande;
		        	
		            writer = new PrintWriter(connexion.getOutputStream(), true);
		            reader = new BufferedInputStream(connexion.getInputStream());
		            commande = username;//first send name for thread's identification, only once
		            writer.write(commande);
		            writer.flush();
		            
		            String response = read();
		            if (response.equals("*")==false){
		            	System.out.print(response);
		            }            
		         } catch (IOException e1) {
		            e1.printStackTrace();
		         }
		         
		         try {
		            Thread.currentThread().sleep(2000);
		         } catch (InterruptedException e) {
		            e.printStackTrace();
		         }      
	        }
   }
   
   private String read() throws IOException{      
      String response = "";
      int stream;
      byte[] b = new byte[4096];
      stream = reader.read(b);
      response = new String(b, 0, stream);      
      return response;
   }   
}