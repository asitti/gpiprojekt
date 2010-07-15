package paper4all.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import paper4all.webservices.utils.Sended;
import paper4all.wsdl.*;

public class Main 
{
	

						
					
	
	
	public void runProgram()
	{
		/*ReadInboxWebService read = new ReadInboxService().getReadInboxWebServicePort();
		String name = read.readFromInbox();
		if(name != null)
			System.out.println(name);
		else
			System.out.println("null");
		
		WriteOutboxWebService write = new WriteOutboxService().getWriteOutboxWebServicePort();
		System.out.println(write.writeToOutbox("in1.xml"));*/
		
		//EDIToXMLWebService edi = new EDIToXMLService().getEDIToXMLWebServicePort();
		//System.out.println(edi.ediToXml(getInput(new File("in/in.edi"))));
		
		OrderProcessorWebService order = new OrderProcessorService().getOrderProcessorWebServicePort();
		String invoice_xml = order.processIncomingOrder(getInput(new File("in/order_f.xml")), 
				getInput(new File("src/paper4all/templates/invoic_template.xml")));
		XMLToEDIWebService s = new XMLToEDIService().getXMLToEDIWebServicePort();
		String invoice_edi = s.xmlToEdi(invoice_xml);
		System.out.println(invoice_edi);
		//System.out.println(order.processIncomingOrder(getInput(new File("in/order_f.xml")), 
				//getInput(new File("src/paper4all/templates/invoic_template.xml"))));
		
	}
	public static void main( String[] args )
	{   
	  
		Main stub = new Main();
		stub.runProgram();
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
  
	public static String display(Sended obj)
	{
		return "[SGTIN: " + obj.getSgtin()
				+ ", GTIN: " + obj.getGtin()
				+ ", VERLANGT: " + obj.getVerlangt()
				+ ", GESCHIKT: " + obj.getGeschickt()
				+ ", PREIS: " + obj.getPreis()
				+"]";
			
	}
}