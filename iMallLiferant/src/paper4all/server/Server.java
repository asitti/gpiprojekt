package paper4all.server;

import javax.swing.JOptionPane; 
import javax.xml.ws.Endpoint;

import paper4all.services.DeleteFile;
import paper4all.services.EDIToXML;
import paper4all.services.OrderProcessor;
import paper4all.services.ReadInbox;
import paper4all.services.WriteOutbox;
import paper4all.services.XMLToEDI;

public class Server 
{ 
  public static void main( String[] args ) 
  {
	  /*Endpoint endpoint1 = Endpoint.publish( "http://localhost:8080/buchhaltung", 
	                                          new Buchhaltung() ); 
	  Endpoint endpoint2 = Endpoint.publish( "http://localhost:8080/lager", 
            new Lager()); */
	  
	  Endpoint endpoint1 = Endpoint.publish( "http://localhost:8080/readinbox", 
              new ReadInbox() ); 
	  Endpoint endpoint2 = Endpoint.publish( "http://localhost:8080/deletefile", 
              new DeleteFile() ); 
	  Endpoint endpoint3 = Endpoint.publish( "http://localhost:8080/writeoutbox", 
              new WriteOutbox() ); 
	  Endpoint endpoint4 = Endpoint.publish( "http://localhost:8080/xmltoedi", 
              new XMLToEDI() ); 
	  Endpoint endpoint5 = Endpoint.publish( "http://localhost:8080/editoxml", 
              new EDIToXML() ); 
	  Endpoint endpoint6 = Endpoint.publish( "http://localhost:8080/orderprocessor", 
              new OrderProcessor() ); 
	  
     
  } 
}