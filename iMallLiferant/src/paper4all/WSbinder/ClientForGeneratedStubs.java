package paper4all.WSbinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.List;

import paper4all.webservices.BuchhaltungService;
import paper4all.webservices.BuchhaltungWebService;
import paper4all.webservices.LagerService;
import paper4all.webservices.LagerWebService;

public class ClientForGeneratedStubs 
{
	
	public void runApp()
	{
		File f = new File("in");
		while(true)
		{
			if(f.isDirectory())
			{
				//f.get
			}
		}
	}
	public static void main( String[] args )
	{   
	  
		ClientForGeneratedStubs stub = new ClientForGeneratedStubs();
		stub.runApp();
		  BuchhaltungWebService buchPort = new BuchhaltungService().getBuchhaltungWebServicePort();
		  File file = new File("files/file");
		  System.out.println("Buchhaltung - sending input from a file and receving:" + buchPort.receiveAString(getInput(file)));
		  
		  System.out.println("-------------------------------------------------");
		  LagerWebService lagerPort = new LagerService().getLagerWebServicePort();
		  List<paper4all.webservices.Sended> send = lagerPort.sendedProducts();
		  for(int i=0;i<send.size();i++)
			  System.out.println(display(send.get(i)));
	 } 
  
	public static String getInput(File f)
	{
		try
		{
			String input = "";
		    BufferedReader in = new BufferedReader(new FileReader(f));
		    String str;
		    while ((str = in.readLine()) != null) 
		    {
		        input += str;
		    }
		    in.close();
		    return input;
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		  
		}
		return null;
	  
	}
  
	public static String display(paper4all.webservices.Sended obj)
	{
		return "[SGTIN: " + obj.getSgtin()
				+ ", GTIN: " + obj.getGtin()
				+ ", VERLANGT: " + obj.getVerlangt()
				+ ", GESCHIKT: " + obj.getGeschickt()
				+ ", PREIS: " + obj.getPreis()
				+"]";
			
	}
}