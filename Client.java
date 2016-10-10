import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable{
   private Socket connexion = null;
   private PrintWriter writer = null;
   private String username;
   private boolean isconnected=true;
   
      public Client(String host, int port,String name){
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
	            
	            if (i==0){
	            	commande = username;//first send name for thread's identification, only once
			        //wait 2 seconds so that the ClientRead thread begins before Client thread 
	            	try {
				            Thread.currentThread().sleep(2000);
				         } catch (InterruptedException e) {
				            e.printStackTrace();
				         } 
	            	i=i+1;
	            }
	            else{ 
	            	commande =  username+":"+getCommand();
	            }
	            	            	           	            
	            writer.write(commande);
	            writer.flush(); 
	            
	            /*
	            if (commande.toUpperCase().equals("QUIT")){
	            	System.out.println("the conversation is terminated");
	            	isconnected=false;	            
	            } 
	            */        
	         } catch (IOException e1) {
	            e1.printStackTrace();
	         }
	         
	         try {
	            Thread.currentThread().sleep(500);
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	         }      
        }
      writer.close();
   }

   private String getCommand(){
	   Scanner s = new Scanner(System.in);
	   String str = null;
	   str = s.nextLine();
       return str;
      }
}