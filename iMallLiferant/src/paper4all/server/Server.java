package paper4all.server;

import javax.swing.JOptionPane; 
import javax.xml.ws.Endpoint;

import paper4all.webservices.Buchhaltung;
import paper4all.webservices.Lager;

public class Server 
{ 
  public static void main( String[] args ) 
  { 
    Endpoint endpoint1 = Endpoint.publish( "http://localhost:8080/buchhaltung", 
                                          new Buchhaltung() ); 
    Endpoint endpoint2 = Endpoint.publish( "http://localhost:8080/lager", 
            new Lager()); 
     
  } 
}