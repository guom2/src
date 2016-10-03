import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
//import java.net.InetSocketAddress;


public class ClientProcessor implements Runnable{

   private Socket sock;
   private PrintWriter writer = null;
   private BufferedInputStream reader = null;

   public ClientProcessor(Socket pSock){
      sock = pSock;
   }

   //Le traitement lancé dans un thread séparé
   public void run(){
      System.err.println("Lancement du traitement de la connexion cliente");
      boolean closeConnexion = false;

      //tant que la connexion est active, on traite les demandes
      while(!sock.isClosed()){
         try {

            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedInputStream(sock.getInputStream());
            
            //On attend la demande du client
            String response = read();
            //InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();
            
            //Debugging info
            /*String debug = "";
            debug = "Thread : " + Thread.currentThread().getName() + ". ";
            debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
            debug += " Sur le port : " + remote.getPort() + ".\n";
            debug += "\t -> Commande reçue : " + response + "\n";
            System.err.println("\n" + debug);*/

            

            //On traite la demande du client en fonction de la commande envoyée
            String toSend = "";
            switch(response.toUpperCase()){
            
               case "TEST":
            	  toSend = "Test réussi";
            
               case "CLOSE":
                  toSend = "Communication terminée";
                  closeConnexion = true;
                  break;

               default :
                  toSend = "Commande inconnue !";
                  break;

            }

            

            //On envoie la réponse au client
            writer.write(toSend);

            writer.flush();
            
            if(closeConnexion){

               System.err.println("Commande close reçue ! ");
               writer = null;
               reader = null;
               sock.close();
               break;
            }

         } catch(SocketException e){
            System.err.println("La connexion a été interrompue ! ");
            break;
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   

   //La méthode que nous utilisons pour lire les réponses

   private String read() throws IOException{      

      String response = "";
      int stream;
      byte[] b = new byte[4096];
      stream = reader.read(b);
      response = new String(b, 0, stream);
      return response;

   }

   
   
}