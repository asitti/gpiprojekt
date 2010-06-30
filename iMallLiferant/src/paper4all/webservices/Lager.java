package paper4all.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.jws.*; 
import javax.jws.soap.SOAPBinding; 

import paper4all.webservices.utils.Sended;
 
@WebService(name="LagerWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class Lager
{ 
	
  @WebMethod 
  public String helloLager() 
  { 
    return "Hello from LagerWebService! :D "; 
  } 
  
 
  @WebMethod(operationName="add-two-numbers") 
  @WebResult(name = "add-result") 
  public double add( @WebParam(name="first") double first, 
                     @WebParam(name="second") double second ) 
  { 
    return first + second; 
  } 
  
  @WebMethod(operationName="sended-products") 
  @WebResult(name = "sended-result") 
  public List<Sended> send() 
  {
	  List<Sended>  list = new ArrayList<Sended>();
	  list.add(new Sended("gtin1","sgtin1",2.13,23,23));
	  list.add(new Sended("gtin2","sgtin2",1.13,16,12));
	  list.add(new Sended("gtin3","sgtin3",3.0,5,3));
	  return list;
    
  } 
  
  
}