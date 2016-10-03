import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{

   private Socket connexion;
   private PrintWriter writer;
   private BufferedInputStream reader;
   private String username;

   
   public Client(String host, int port, String name){
      try {
    	 this.username = name;
         connexion = new Socket(host, port);
         
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }


   public void run(){
	   
         try {
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());

            String commande = getCommand();

            writer.write(commande);
            writer.flush();  
            System.out.println("Commande " + commande + " envoyée au serveur");

            

            //On attend la réponse

            String response = read();
            System.out.println("\t * " + username + " : Réponse reçue : " + response);

            

         } catch (IOException e1) {
            e1.printStackTrace();
         }

         

         try {

            //Thread.currentThread().sleep(1000);
        	 Thread.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }

      

      //writer.write("CLOSE");
      //writer.flush();
      //writer.close();

   }

   

   //Méthode qui permet d'envoyer des commandeS de façon aléatoire

   private String getCommand(){
	  return("TEST");
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