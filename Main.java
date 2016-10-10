/*
 * Version 1.0 03/10 construction of serveur and clients on one computer, on one console
 * Version 1.1 10/10 enable the stock of messages not sent in server, and message constant on one computer
 * 
 * 
 * TO DO
 * use different computer. host address?
 * graphique representation
 * Add liste d'utilisateurs (nom, mot de passe etc)
 */

public class Main {
   public static void main(String[] args) {

      String host = "127.0.0.15";
      int port = 2345;
      
      Server ts = new Server(host, port);
      ts.open();
      
      System.out.println("Serveur initialise.");
   }
}
