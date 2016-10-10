import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientProcessor implements Runnable{

	   private Socket sock;
	   private PrintWriter writer = null;
	   private BufferedInputStream reader = null;
	   static private ArrayList<String> conversations= new ArrayList<String>();
	   private String name="";
	   
	   public ClientProcessor(Socket pSock){
	      sock = pSock;
	   }
   
   public void run(){

      boolean closeConnexion = false;
      while(!sock.isClosed()){
         
         try {
            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedInputStream(sock.getInputStream());
                      
            String response = read();
            
            InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();

            String toSend="";
            if (response.indexOf(":")==-1){//this if is to identify the user name, which is used to detected the message. then how to instant the message?
            	name = response;
            }
            else{
            	conversations.add(response);
            	/*//to detecte the close signaux
	            switch(response.toUpperCase()){
	               case "QUIT":
	                  closeConnexion = true;
	                  break;
	               default : 
	            	  //System.out.println(response);
	            	  conversations.add(response);
	                  //System.out.println(conversations);
	                  break;	              
	            }
	            */
            }            	
            	//scan all the conversations to check if there are messages left for this user
	            if (conversations.size()>0){
	            	int i=0;
	            	while (i<conversations.size()){
	            		//if the String i contains user's name as tag, then add it to String toSend, and delete it from conversations stock
	            		if (conversations.get(i).substring(conversations.get(i).indexOf(":")+1,conversations.get(i).lastIndexOf(":")).equals(name)){
		            		toSend=toSend+conversations.get(i).substring(0,conversations.get(i).indexOf(":"))+" said to you: ";
		            		toSend=toSend+conversations.get(i).substring(conversations.get(i).lastIndexOf(":")+1)+"\n";
		            		conversations.remove(i);
	            		}
	            		else{
	            			i=i+1;			
	            		}
	            	}	                        
	            }
	            //to assure that the string toSend has something to send out
	            if (toSend.equals("")){
	            	toSend="*";
	            }
            
            writer.write(toSend);
            writer.flush();
            
            if(closeConnexion){
               System.err.println("Commande close detected");
               writer = null;
               reader = null;
               sock.close();
               break;
            }
         }catch(SocketException e){
            System.err.println("The connection has been interrupted");
            break;
         } catch (IOException e) {
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