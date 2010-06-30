package paper4all.webservices;

import java.io.File;
import java.io.FileWriter;

import javax.jws.*; 
import javax.jws.soap.SOAPBinding; 
 
@WebService(name="BuchhaltungWebService") 
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT) 
public class Buchhaltung
{ 
	
	  @WebMethod 
	  public String helloBuchhaltung() 
	  { 
	    return "Hello from BuchhaltungWebService! :D "; 
	  } 
	  
	 
	  @WebMethod(operationName="multiply-two-numbers") 
	  @WebResult(name = "multiply-result") 
	  public double multiply( @WebParam(name="first") double first, 
	                     @WebParam(name="second") double second ) 
	  { 
	    return first * second; 
	  }
	  
	  @WebMethod(operationName="receive-a-string") 
	  @WebResult(name = "fileName-result") 
	  public String fileReceive( @WebParam(name="content")String s ) 
	  {
		  try
		  {
			  if(s != null)
			  {
				  File f = new File("newFile");
				  FileWriter writer = new FileWriter(f);
				  writer.write(s);
				  writer.close();
				  return "[fileName: "+ f.getName() + " fileInput: " + s + "]";				  
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  
		  return null; 
	  }
}