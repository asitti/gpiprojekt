package paper4all.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import paper4all.wsdl.*;

public class Main 
{
	public void runProgram()
	{
		
		/*while(true)
		{
			System.out.print("reading hub content for incoming orders.....");
			ReadInboxWebService read = new ReadInboxService().getReadInboxWebServicePort();
			String bestellInput = read.readFromInbox();
			if(bestellInput != null)
			{
				System.out.println("OK");
				//System.out.println(bestellInput);
					
				System.out.print("transforming incoming order - edi to xml.....");
				EDIToXMLWebService edi = new EDIToXMLService().getEDIToXMLWebServicePort();
				String bestell = edi.ediToXml(bestellInput);
				if(bestell !=null)
				{
					System.out.println("OK");
					//System.out.println(bestell);
					
					System.out.print("processing order.....");
					OrderProcessorWebService order = new OrderProcessorService().getOrderProcessorWebServicePort();
					List<String> xmls = order.processIncomingOrder(bestell, //getInput(new File("in/order_f.xml")) 
							getInput(new File("src/paper4all/templates/invoic_template.xml"))
							, getInput(new File("src/paper4all/templates/desadv_template.xml")));
					
					if(xmls != null)
					{
						System.out.println("OK");
						
						//System.out.println("rechnung: " + xmls.get(0));
						//System.out.println("lieferavis: " + xmls.get(1));
						
						
						XMLToEDIWebService x2e = new XMLToEDIService().getXMLToEDIWebServicePort();
						System.out.println("transforming invoice - xml to edi.....");
						String rechnung = x2e.xmlToEdi(xmls.get(0));
						
						if(rechnung != null)
						{
							System.out.println("OK");
							System.out.print("writing invoice to outbox.....");
							WriteOutboxWebService write = new WriteOutboxService().getWriteOutboxWebServicePort();
							boolean ok = write.writeToOutbox(rechnung);
							if(ok)
								System.out.println("OK");
							else
								System.out.println("ERROR");
						}
						
						String lieferavis = x2e.xmlToEdi(xmls.get(1));
						System.out.println("transforming desadv - xml to edi.....");
						if(lieferavis != null)
						{
							System.out.println("OK");
							System.out.print("writing desadv to outbox.....");
							WriteOutboxWebService write = new WriteOutboxService().getWriteOutboxWebServicePort();
							boolean ok = write.writeToOutbox(lieferavis);
							if(ok)
								System.out.println("OK");
							else
								System.out.println("ERROR");
						}
												
						System.out.println();
					}
				}
			}
		}*/
		
		/*ReadInboxWebService read = new ReadInboxService().getReadInboxWebServicePort();
		String name = read.readFromInbox();
		if(name != null)
			System.out.println(name);
		else
			System.out.println("null");
		
		WriteOutboxWebService write = new WriteOutboxService().getWriteOutboxWebServicePort();
		System.out.println(write.writeToOutbox("in1.xml"));*/
		
		EDIToXMLWebService edi = new EDIToXMLService().getEDIToXMLWebServicePort();
		String bestell = edi.ediToXml(getInput(new File("in/544.edi")));
		System.out.println("bestell: " + bestell);
		
		OrderProcessorWebService order = new OrderProcessorService().getOrderProcessorWebServicePort();
		List<String> xmls = order.processIncomingOrder(bestell, //getInput(new File("in/order_f.xml")) 
				getInput(new File("src/paper4all/templates/invoic_template.xml"))
				, getInput(new File("src/paper4all/templates/desadv_template.xml")));
		System.out.println("rechnung: " + xmls.get(0));
		System.out.println("lieferavis: " + xmls.get(1));
		
		//XMLToEDIWebService s = new XMLToEDIService().getXMLToEDIWebServicePort();
		//String invoice_edi = s.xmlToEdi(invoice_xml.get(0));
		//System.out.println(invoice_edi);
		//System.out.println(order.processIncomingOrder(getInput(new File("in/order_f.xml")), 
				//getInput(new File("src/paper4all/templates/invoic_template.xml"))));
		
		//WriteOutboxWebService write = new WriteOutboxService().getWriteOutboxWebServicePort();
		//System.out.println(write.writeToOutbox(invoice_edi));
		
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
  
}