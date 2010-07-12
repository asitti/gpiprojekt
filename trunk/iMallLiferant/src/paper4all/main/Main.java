package paper4all.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import paper4all.rubyParser.Interchange;
import paper4all.webservices.BuchhaltungService;
import paper4all.webservices.BuchhaltungWebService;

public class Main 
{
	
	public void runApp()
	{
		try
		{
			BuchhaltungWebService buchPort = new BuchhaltungService().getBuchhaltungWebServicePort();
			
			//directorul din care citim mesajele primite momentan, pana merge din noul hubul
			File f = new File("in");
			//while(true)
			//{
				if(f.isDirectory())
				{
					String[] files = f.list(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) 
						{
							if (name.endsWith(".xml")) return true;
							   return false;
						}
					});
					System.out.println("The number of files: " + files.length);
					for(int i=0; i<files.length; i++)
					{
						System.out.println("processing file: " + files[i]);
						File currentFile = new File("in/" + files[i]);
						String input = this.getInput(currentFile);
						//System.out.println("an the input looks like this: " + input);
						
						buchPort.parseBestellungen(input);
						
						
						
						/* JAXBContext jc = JAXBContext.newInstance("paper4all.rubyParser");
						 Unmarshaller u = jc.createUnmarshaller();
						 System.out.println("intoarce ceva...");
						 Interchange interchange = (Interchange ) u.unmarshal(currentFile);
						 System.out.println("unmarshal..." + interchange.getVersion());*/
						
						
						/*Interchange inter = buchPort.parseBestellungen(input);
						if(inter != null)
							System.out.println(inter.getVersion());
						else
							System.out.println("buba");*/
						
						//la sf stergem fisierul ca nu mai avem nevoie de el
						
						/*boolean delete = currentFile.delete();
						System.out.println("an the file was deleted: " + delete);
						*/
						System.out.println();
					}
				}
			//}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void runProgram()
	{
		
		
	}
	public static void main( String[] args )
	{   
	  
		Main stub = new Main();
		stub.runProgram();
		  /*BuchhaltungWebService buchPort = new BuchhaltungService().getBuchhaltungWebServicePort();
		  File file = new File("files/file");
		  System.out.println("Buchhaltung - sending input from a file and receving:" + buchPort.receiveAString(getInput(file)));
		  
		  System.out.println("-------------------------------------------------");
		  LagerWebService lagerPort = new LagerService().getLagerWebServicePort();
		  List<paper4all.webservices.Sended> send = lagerPort.sendedProducts();
		  for(int i=0;i<send.size();i++)
			  System.out.println(display(send.get(i)));*/
	 } 
  
	private String getInput(File f)
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