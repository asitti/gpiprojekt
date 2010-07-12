package paper4all.webservices;

import java.io.File;
import java.io.FileWriter;

import javax.jws.*; 
import javax.jws.soap.SOAPBinding; 
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import paper4all.rubyParser.Interchange;
 
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
	  
	 /* @WebMethod(operationName="parse-bestellungen") 
	  @WebResult(name = "interchange-result") 
	  public MyInterchange parseBest( @WebParam(name="inputFile")String s) 
	  {
		  try
		  {
			  if(s != null)
			  {
				  File f = new File("temp/tempFile.xml");
				  FileWriter writer = new FileWriter(f);
				  writer.write(s);
				  writer.close();
				  
				  JAXBContext jc = JAXBContext.newInstance("paper4all.rubyParser");
				  Unmarshaller u = jc.createUnmarshaller();
				  System.out.println("intoarce ceva...");
				  Interchange interchange = (Interchange ) u.unmarshal(f);
				  System.out.println("unmarshal..." + interchange.getVersion());
				
				   return new MyInterchange(interchange);
				  
				 // return interchange;
				  				  
			  }
		  }
		  catch (JAXBException e) 
		  {
			 // TODO Auto-generated catch block
			  System.out.println("here");
			 e.printStackTrace();
		  }
		  catch(Exception e)
		  {
			  System.out.println("here2");
			  e.printStackTrace();
		  }
		  return null;
	  }
	  */

}